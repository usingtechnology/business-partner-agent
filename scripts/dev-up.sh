#!/bin/bash
# Copyright (c) 2020 - for information on the respective copyright owner
# see the NOTICE file and/or the repository at
# https://github.com/hyperledger-labs/organizational-agent
#
# SPDX-License-Identifier: Apache-2.0

docker-compose --profile second_bpa --profile third_bpa -f docker-compose.yml -f docker-compose.override.yml up bpa-agent1 bpa2 bpa3
