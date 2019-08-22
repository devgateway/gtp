#!/bin/sh

SRC="forms/target/forms.jar"
TARGET="/opt/ad3/ad3.jar"

if [ -f $SRC ]; then
  cp $SRC $TARGET
  sudo systemctl restart ad3
else
  echo "File not found: $SRC"
  exit 1
fi
