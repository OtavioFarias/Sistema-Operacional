#!/bin/bash

# Cabeçalho do CSV
echo "tamanhoMem,minBloco,requisicoes, - ,cleaningPercent,execucao,real_time,user_time,sys_time,max_memory_kb" > resultados.csv

# Loop para os 6 parâmetros (exemplos simples — substitua conforme necessário)
for p1 in 1024 2048 4096 8192; do
  for p2 in 2 4 8 16 32 64; do
    for p3 in 10 20 30 40 50 60 100 1000; do
    #for p3 in 10 ; do
      for p4 in 1; do
        for p5 in 0 10 20 30 40 50 60 70 80 90 100; do
          for p6 in 1; do
            # Captura tempo e memória com o /usr/bin/time (externo)
            METRICS=$( { /usr/bin/time -f "%e,%U,%S,%M" java buddy.Main "$p1" "$p2" "$p3" "$p4" "$p5" 1>/dev/null; } 2>&1 )
            echo "$p1,$p2,$p3,$p4,$p5,$p6,$METRICS" >> resultados.csv
          done
        done
      done
    done
  done
done
