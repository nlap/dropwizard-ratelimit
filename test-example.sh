#!/bin/bash

for i in 1 2 3 4
do
	curl -X "GET" "http://localhost:8080"
	echo ""
done