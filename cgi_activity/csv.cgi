#!/bin/sh

echo "Content-type:text/html"
echo ""

java -cp .:commons-lang3-3.6.jar:commons-text-1.1.jar:commons-beanutils-1.9.3.jar:opencsv-4.0.jar CSVTOHTML
