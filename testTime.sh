#!/bin/bash

for p4 in 1; do
	for p1 in 1 2 3 4 5 6 7 8 9 10; do
		java singleThread/buddy.Main 1024 64 10000000 "$p4" 0
	done
done
