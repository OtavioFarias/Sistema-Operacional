JAVA_CLASS=tools.Main

run:
	@for i in 0 10 20 30 40 50 60 70 80 90 100; do \
	  for j in 4 8 16 32 64 128 256 512; do \
	    for k in 1024 2048 4096 8192 16384; do \
	      for r in 1 2 3 4 5 6 7 8 9 10; do \
	        echo "Execução $$r: java $(JAVA_CLASS) $$k 16 1024 10000 $$i $$j"; \
	        java $(JAVA_CLASS) $$k 16 1024 10000 $$i $$j; \
	      done; \
	    done; \
	  done; \
	done
