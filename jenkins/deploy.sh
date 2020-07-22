#!/bin/bash

GENERATE_SOURCEMAP=false
SRC="forms/target/forms.jar"
TARGET="/opt/ad3/ad3.jar"

TODAY=$(date +%F)
if [ "$TODAY" == "$AD3_CLOCK_DATE" ]; then
  CLOCK_PATTERN='s/^#?(AD3_CLOCK_DATE)/#\1/'
else
  CLOCK_PATTERN="s/^#?(AD3_CLOCK_DATE).*/\1=$AD3_CLOCK_DATE/"
fi
CONFIG=$(sed -E "$CLOCK_PATTERN" /etc/default/ad3)
echo "$CONFIG" > /etc/default/ad3

if [ "$RECREATE_DB" = "true" ] && [ -f "/opt/ad3/recreate-db.sh" ]; then
  sudo systemctl stop ad3
  /bin/sh -xe /opt/ad3/recreate-db.sh
fi

if [ -f $SRC ]; then
  cp $SRC $TARGET
  sudo systemctl restart ad3
else
  echo "File not found: $SRC"rp
  exit 1
fi
