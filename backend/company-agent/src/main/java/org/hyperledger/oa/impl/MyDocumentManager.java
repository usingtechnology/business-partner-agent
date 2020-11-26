/*
  Copyright (c) 2020 - for information on the respective copyright owner
  see the NOTICE file and/or the repository at
  https://github.com/hyperledger-labs/business-partner-agent

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package org.hyperledger.oa.impl;

import lombok.NonNull;
import org.hyperledger.oa.api.MyDocumentAPI;
import org.hyperledger.oa.api.exception.WrongApiUsageException;
import org.hyperledger.oa.impl.activity.DocumentValidator;
import org.hyperledger.oa.impl.activity.VPManager;
import org.hyperledger.oa.impl.aries.LabelStrategy;
import org.hyperledger.oa.impl.aries.SchemaService;
import org.hyperledger.oa.impl.util.Converter;
import org.hyperledger.oa.model.MyDocument;
import org.hyperledger.oa.repository.MyDocumentRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class MyDocumentManager {

    @Inject
    MyDocumentRepository docRepo;

    @Inject
    VPManager vp;

    @Inject
    Converter converter;

    @Inject
    Optional<LabelStrategy> labelStrategy;

    @Inject
    Optional<SchemaService> schemaService;

    @Inject
    DocumentValidator validator;

    public MyDocumentAPI saveNewDocument(@NonNull MyDocumentAPI apiDoc) {
        validator.validateNew(apiDoc);

        labelStrategy.ifPresent(strategy -> strategy.apply(apiDoc));
        final MyDocument dbDoc = docRepo.save(converter.toModelObject(apiDoc));

        if (apiDoc.getIsPublic()) { // new credential, so no need to change the VP when it's private
            vp.recreateVerifiablePresentation();
        }
        return convertAndResolve(dbDoc);
    }

    public MyDocumentAPI updateDocument(@NonNull UUID id, @NonNull MyDocumentAPI apiDoc) {
        final Optional<MyDocument> dbCred = docRepo.findById(id);
        if (dbCred.isPresent()) {
            validator.validateExisting(dbCred, apiDoc);

            labelStrategy.ifPresent(strategy -> strategy.apply(apiDoc));
            MyDocument dbDoc = converter.updateMyCredential(apiDoc, dbCred.get());
            docRepo.update(dbDoc);

            // update, so we always need to check, only exception private stays private
            vp.recreateVerifiablePresentation();

            return convertAndResolve(dbDoc);
        }
        throw new WrongApiUsageException("No document with id " + id + " found.");
    }

    public List<MyDocumentAPI> getMyDocuments() {
        List<MyDocumentAPI> result = new ArrayList<>();
        docRepo.findAll().forEach(dbDoc -> result.add(convertAndResolve(dbDoc)));
        return result;
    }

    public Optional<MyDocumentAPI> getMyDocumentById(@NonNull UUID id) {
        Optional<MyDocument> myDoc = docRepo.findById(id);
        return myDoc.map(dbDoc -> convertAndResolve(dbDoc));
    }

    public void deleteMyDocumentById(@NonNull UUID id) {
        docRepo.deleteById(id);
        vp.recreateVerifiablePresentation();
    }

    private MyDocumentAPI convertAndResolve(MyDocument dbDoc) {
        MyDocumentAPI apiDoc = converter.toApiObject(dbDoc);
        schemaService.ifPresent(s -> apiDoc.setTypeLabel(s.getSchemaLabel(dbDoc.getSchemaId())));
        return apiDoc;
    }

}
