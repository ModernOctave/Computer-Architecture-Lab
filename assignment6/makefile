name = $(shell basename '$(CURDIR)')

make:
	ant make-jar

run: make
	java -jar jars/simulator.jar src/configuration/config.xml stats.txt  test_cases/$(prog).out

benchmark: make
	java -jar jars/simulator.jar src/configuration/config.xml stats.txt  benchmarks/$(prog).out
	
cbench: compile benchmark

compile:
	java -jar ../assignment2/jars/assembler.jar benchmarks/$(prog).asm benchmarks/$(prog).out

zip:
	rm -rf submissions
	zip -FSr --quiet 200010036_200010037_$(name).zip src 200010036_200010037_$(name).pdf

testzip: zip
	python2 test_zip.py 200010036_200010037_$(name).zip

clean:
	rm -rf bin jars submissions 200010036_200010037_$(name).zip stats.txt