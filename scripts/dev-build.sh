#!/bin/bash
# Copyright (c) 2020 - for information on the respective copyright owner
# see the NOTICE file and/or the repository at
# https://github.com/hyperledger-labs/organizational-agent
#
# SPDX-License-Identifier: Apache-2.0

docker-compose -f docker-compose.yml -f docker-compose.override.yml build --no-cache --force-rm --pull bpa1
