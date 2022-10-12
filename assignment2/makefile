name = $(shell basename '$(CURDIR)')

make:
	ant make-jar

run: make
	java -jar jars/assembler.jar test_cases/$(prog).asm $(prog).out

zip:
	zip -FSr --quiet 200010036_200010037_$(name).zip src 200010036_200010037_$(name).pdf

testzip: zip
	python2 test_zip.py 200010036_200010037_$(name).zip

clean:
	rm -rf bin jars submissions 200010036_200010037_$(name).zip stats.txt