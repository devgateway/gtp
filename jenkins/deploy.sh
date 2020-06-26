#!/bin/bash

GENERATE_SOURCEMAP=false
SRC="forms/target/forms.jar"
TARGET="/opt/ad3/ad3.jar"

TODAY=`date +%F`
if [ "$TODAY" == "$AD3_CLOCK_DATE" ]; then
  sed -Ei 's/^#?(AD3_CLOCK_DATE)/#\1/' /etc/default/ad3
else
  sed -Ei "s/^#?(AD3_CLOCK_DATE).*/\1=$AD3_CLOCK_DATE/" /etc/default/ad3
fi

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
