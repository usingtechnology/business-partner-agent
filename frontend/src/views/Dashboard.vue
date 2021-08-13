<!--
 Copyright (c) 2020 - for information on the respective copyright owner
 see the NOTICE file and/or the repository at
 https://github.com/hyperledger-labs/organizational-agent

 SPDX-License-Identifier: Apache-2.0
-->
<template>
  <v-container text-center>
    <div v-if="isWelcome && !isLoading">
      <!-- Image from undraw.co -->
      <v-img
        class="mx-auto"
        src="@/assets/undraw_welcome_3gvl_grey.png"
        max-width="300"
        aspect-ratio="1"
      ></v-img>
      <p
        v-bind:style="{ fontSize: `180%` }"
        class="grey--text text--darken-2 font-weight-medium"
      >
        Hi, we've already set up an identity for you!
      </p>
      <!-- <p v-bind:style="{ fontSize: `140%` }" class="grey--text text--darken-2 font-weight-medium">Start by adding a public profile that your business partners will see</p> -->
      <br />
      <v-bpa-button
        color="primary"
        :to="{
          name: 'DocumentAdd',
          params: { type: CredentialTypes.PROFILE.type },
        }"
        >Setup your Profile</v-bpa-button
      >
    </div>
    <div v-if="!isWelcome && !isLoading">
      <v-row>
          <v-col class="col-12 col-sm-6 col-md-4">
            <v-card class="mx-auto">
              <v-card-title class="justify-center bg-light" style="text-transform: capitalize">Credentials you hold</v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col class="col-6">
                      <v-icon color="black" size="120">$vuetify.icons.wallet</v-icon>
                    </v-col>
                    <v-col class="col-6">
                      <span class="cardTitle" style="color:black;">{{ status.credentials }}</span>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions class="justify-center">
                <v-btn text link :to="{ name: 'Partners' }">View <v-icon>$vuetify.icons.next</v-icon></v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
          <v-col class="col-12 col-sm-6 col-md-4">
          <v-card class="mx-auto">
            <v-card-title class="justify-center bg-light" style="text-transform: capitalize">Sent proof Requests</v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col class="col-6">
                    <v-icon color="black" size="120">$vuetify.icons.proofRequests</v-icon>
                  </v-col>
                  <v-col class="col-6">
                    <span class="cardTitle" style="color:black;">{{ status.credentials }}</span>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn text link :to="{ name: 'Partners' }">View <v-icon>$vuetify.icons.next</v-icon></v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
          <v-col class="col-12 col-sm-6 col-md-4">
            <v-card class="mx-auto">
              <v-card-title class="justify-center bg-light" style="text-transform: capitalize">Business Partners</v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col class="col-6">
                      <v-icon color="black" size="120">$vuetify.icons.partners</v-icon>
                    </v-col>
                    <v-col class="col-6">
                      <span class="cardTitle" style="color:black;">{{ status.partners }}</span>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions class="justify-center">
                <v-btn text link :to="{ name: 'Partners' }">View <v-icon>$vuetify.icons.next</v-icon></v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        <v-col class="col-12 col-sm-6 col-md-4">
          <v-card class="mx-auto">
            <v-card-title class="justify-center bg-light" style="text-transform: capitalize">Credentials you issued</v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col class="col-6">
                    <v-icon color="black" size="120">$vuetify.icons.credentialManagement</v-icon>
                  </v-col>
                  <v-col class="col-6">
                    <span class="cardTitle" style="color:black;">{{ status.credentials }}</span>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn text link :to="{ name: 'CredentialManagement' }">View <v-icon>$vuetify.icons.next</v-icon></v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col class="col-12 col-sm-6 col-md-4">
          <v-card class="mx-auto">
            <v-card-title class="justify-center bg-light" style="text-transform: capitalize">Received proof Requests</v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col class="col-6">
                    <v-icon color="black" size="120">$vuetify.icons.proofRequests</v-icon>
                  </v-col>
                  <v-col class="col-6">
                    <span class="cardTitle" style="color:black;">{{ status.credentials }}</span>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn text link :to="{ name: 'Partners' }">View <v-icon>$vuetify.icons.next</v-icon></v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col class="col-12 col-sm-6 col-md-4">
          <v-card class="mx-auto">
            <v-card-title class="justify-center bg-light" style="text-transform: capitalize">Pending Actions</v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col class="col-6">
                    <v-icon color="black" size="120">$vuetify.icons.notifications</v-icon>
                  </v-col>
                  <v-col class="col-6">
                    <span class="cardTitle" style="color:black;">{{ status.credentials }}</span>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn text link :to="{ name: 'Notifications' }">View <v-icon>$vuetify.icons.next</v-icon></v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import { EventBus } from "../main";
import { CredentialTypes } from "../constants";
import VBpaButton from "@/components/BpaButton";
export default {
  name: "Dashboard",
  components: { VBpaButton },
  created() {
    EventBus.$emit("title", "Dashboard");
    this.getStatus();
  },
  data: () => {
    return {
      isWelcome: true,
      isLoading: true,
      CredentialTypes,
    };
  },
  computed: {
    partnerNotificationsCount() {
      return this.$store.getters.partnerNotificationsCount;
    },
    credentialNotificationsCount() {
      return this.$store.getters.credentialNotificationsCount;
    },
  },
  methods: {
    getStatus() {
      console.log("Getting status...");
      this.$axios
        .get(`${this.$apiBaseUrl}/status`)
        .then((result) => {
          console.log(result);
          this.isWelcome = !result.data.profile;
          this.status = result.data;
          this.isLoading = false;
        })
        .catch((e) => {
          console.error(e);
          this.isLoading = false;
          EventBus.$emit("error", e);
        });
    },
  },
};
</script>
<style scoped>
.newHighlight {
  color: #2ecc71;
  padding-left: 4px;
  font-size: 200%;
}
.cardTitle {
  font-size: 400%;
  margin-bottom: 2;
}
</style>
