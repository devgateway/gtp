#!/bin/bash

GENERATE_SOURCEMAP=false
SRC="forms/target/forms.jar"
TARGET="/opt/ad3/ad3.jar"

TODAY=$(date +%F)
SED_TEMP=$(mktemp)
if [ "$TODAY" == "$AD3_CLOCK_DATE" ]; then
  sed -E 's/^#?(AD3_CLOCK_DATE)/#\1/' /etc/default/ad3 > $SED_TEMP
else
  sed -E "s/^#?(AD3_CLOCK_DATE).*/\1=$AD3_CLOCK_DATE/" /etc/default/ad3 > $SED_TEMP
fi
cat $SED_TEMP > /etc/default/ad3
rm $SED_TEMP

if [ "$RECREATE_DB" = "true" ] && [ -f "/opt/ad3/recreate-db.sh" ]; then
  sudo systemctl stop ad3
  /opt/ad3/recreate-db.sh
fi

if [ -f $SRC ]; then
  cp $SRC $TARGET
  sudo systemctl restart ad3
else
  echo "File not found: $SRC"rp
  exit 1
fi
