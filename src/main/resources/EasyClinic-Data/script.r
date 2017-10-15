#@sebastiano panichella

#install packages...
install.packages("SnowballC")
install.packages("lsa")
install.packages("stringr")
#load the libraries...
library(SnowballC)
library(lsa)
library(stringr)
#path software artifacts
mydir<-".../EasyClinic-Data/documents"

output1<-".../EasyClinic-Data/rankedlists/ranked_list75.txt"
output2<-".../EasyClinic-Data/rankedlists/ranked_list65.txt"
output3<-".../EasyClinic-Data/rankedlists/ranked_list55.txt"

# the following command index the software artifacts
# and store this data in "tm" 
tm<-textmatrix(mydir, stemming=TRUE, language="english",minWordLength=2, minDocFreq=1, stopwords=NULL, vocabulary=NULL)

uc<-1:30
cc<-114:160
# the following command compute the textual similarity between software artifacts
similarity_matrix<-cosine(tm)
# the following command create a data structure in form of a "list" composed by 3 attributes
# or 3 columns (with initial empy values): 
# source (first software artifact);
# target (second software artifact);
# similarity  (tectual similarity between first and second software artifacts).
ranked_list<-list(source=c(),target=c(),similarity=c())

# in the following for cycle we populate the list "ranked_list"
# by using the information stored in the "similarity_matrix" matrix.
for(i in 1:length(uc)){
  
  for(j in 1:length(cc)){
    pos<-length(ranked_list$source)
    ranked_list$source[pos+1]<-paste(uc[i],".txt",sep="")
    ranked_list$target[pos+1]<-paste(cc[j],".txt",sep="")
    ranked_list$similarity[pos+1]<-similarity_matrix[ranked_list$source[pos+1],ranked_list$target[pos+1]]
  }
}
# the following trasform the ranked_list data structure in a data frame 
ranked_list<-as.data.frame(ranked_list)
# the following order the data.frame rows by textual similarity (third column in the data frame)
ranked_list<-ranked_list[ order(-ranked_list$similarity), ]

# the following selectd the part of the ranked list 
# with threshold equal to 0.70 and assings the resulting data frame to a new variable.
ranked_listThreshold70<-ranked_list[-which(ranked_list$similarity<0.70),]
# the following selectd the part of the ranked list 
# with threshold equal to 0.65 and assings the resulting data frame to a new variable.
ranked_listThreshold65<-ranked_list[-which(ranked_list$similarity<0.65),]
# the following selectd the part of the ranked list 
# with threshold equal to 0.55 and assings the resulting data frame to a new variable.
ranked_listThreshold55<-ranked_list[-which(ranked_list$similarity<0.55),]

# the following commands write the created dataframe
write.csv(ranked_listThreshold70,output1,quote = FALSE,row.names = FALSE)
write.csv(ranked_listThreshold65,output2,quote = FALSE,row.names = FALSE)
write.csv(ranked_listThreshold55,output3,quote = FALSE,row.names = FALSE)


