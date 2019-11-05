ReadTwitter

Feature,

user enter some topics ( up to 5) and this applicaton establish the connection with twitter and filter the stream content 
to accordingly topics and wirte then into plain txt files. 
When user enter "quit", shutdown the twitter connection and close the files. 

some feature need to be implemented in future--
1) currently limit to one twitter stream connection, if there are multiple sets of token, can establish multiple connections
2) file size is not limited, should set each file's max size and rotate files when the file reaches the max size.


Install and run --
 > mvn install
 generate the executable jar file with dependencies: readTwitter-jar-with-dependencies , 
 > java -jar readTwitter-jar-with-dependencies.jar

 System prompts to enter up to five topic without empty string
 
 for example, enter-
 
 >movie,sports,Canada,skate
 
 System response with starting wirtting these topics to files: movie.txt, sports.txt, Canada.txt, skate.txt file
 
 enter "quit" to quit the progrm
 
 There is twitterConfig.propertis file. There are the twitter tokens setting and writting file(s) relative path setting
 in this example, the path is TWITTER_TOPIC. 
 A directory "TWITTER_TOPIC" will be created if there is no such directory, and the files will be under this directory. 
 
 
 