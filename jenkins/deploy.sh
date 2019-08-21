#!/bin/sh

if [ $# -ne 1 ]; then
  echo "Example usage: \n$0 JAR_FILE"
  exit 1
fi

SRC="forms/target/forms.jar"
TARGET="/opt/ad3/ad3.jar"

if [ -f $SRC ]; then
  cp $SRC $TARGET
  systemctl restart ad3
else
  echo "File not found: $SRC"
  exit 1
fi
