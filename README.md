# Text Search Engine 

### Introduction
This is a text search engine that searches through a set of files in a given directory for 
specified texts and displays the percentage matches of the text.  
  
### Matching algorithm
Words are first checked whether they are matching ir not. If they are matching, then check if the
 current word is the next word of the previously matched world. If yes then add 1.0 to the weighted sum or else 0.5. The percentage match is calculated as (weighted sum / total number of words to match) * 100
     
### Requirements

1. Java 8
2. Gradle (local or wrapper)
     
### How to run

1. Clone project
2. run command `gradle clean`
3. run command `gradle fatJar`
4. open `/build/libs/` directory in the terminal
5. run command `java -jar textsearchengine-<version_number>.jar 
<absolute_path_to_folder_containing_files>`
6. enter words to match and press `Enter`
7. type `:quit` to exit

### Known Issues
1. Exception handling is lacking in precision
2. The console UI is not consistent. The phrase marker overlaps with the results due to lack of 
synchronization due to asynchronous IO.  
