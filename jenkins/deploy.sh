#!/bin/sh

SRC="forms/target/forms.jar"
TARGET="/opt/ad3/ad3.jar"

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
