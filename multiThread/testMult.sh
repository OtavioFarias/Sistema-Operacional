#!/bin/bash

echo "tamanhoMem,minBloco,requisicoes,threads,cleaningPercent,execucao" > resultados.csv

for p1 in 1024 2048 4096 8192; do
  for p2 in 2 4 8 16 32 64; do
    for p3 in 1 10 20 30 40 50 60 100 1000 10000 100000 1000000; do
      for p4 in 1 2 4 8 16 32 64; do
        for p5 in 0 10 20 30 40 50 60 70 80 90 100; do
          for p6 in 1; do
            java multiThread/buddy.Main "$p1" "$p2" "$p3" "$p4" "$p5"
          done
        done
      done
    done
  done
done