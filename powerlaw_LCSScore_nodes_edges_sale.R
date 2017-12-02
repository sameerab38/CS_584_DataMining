dist = read.csv("purchase_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount) ~ log(dist$NodeCount))
fit
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("purchase_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount) ~ log(dist$NodeCount))
fit
dist = read.csv("purchase_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount) ~ log(dist$NodeCount))
fit
plot(y = log(dist$EdgeCount), x = log(dist$NodeCount), pch=4, col= "red")
abline(  -1.499            ,    2.286 ) 
savehistory("powerlaw_SimScore_nodes_edges_purchase.R")
# ref for line fitting: http://www.theanalysisfactor.com/linear-models-r-plotting-regression-lines/
C = 10^fit$coefficients[1]
Theta = fit$coefficients[2]
dist$outlierscore = rep(0,dim(dist)[1])
for(i in 1:dim(dist)[1]){
dist$outlierscore[i] = max(C*(dist[i,2]^Theta), dist[i,3]  ) / min(C*(dist[i,2]^Theta), dist[i,3] ) * log(abs(C*(dist[i,2]^Theta)- dist[i,3] ) +1);
}
text(log(dist$NodeCount), log(dist$EdgeCount), labels = sprintf("%.2f",dist$outlierscore) , adj = 1, cex = 0.5)
dist$outlierscore
C
 10^fit$coefficients[1]
 fit$coefficients[1]
10^1
10^-1.4
10^-1.49
log 10
log (10)
log (10,10)
log (4,2)
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("purchase_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(  -1.499            ,    2.286 ) 
savehistory("powerlaw_SimScore_nodes_edges_purchase.R")
# ref for line fitting: http://www.theanalysisfactor.com/linear-models-r-plotting-regression-lines/
C = 10^fit$coefficients[1]
Theta = fit$coefficients[2]
dist$outlierscore = rep(0,dim(dist)[1])
for(i in 1:dim(dist)[1]){
dist$outlierscore[i] = max(C*(dist[i,2]^Theta), dist[i,3]  ) / min(C*(dist[i,2]^Theta), dist[i,3] ) * log(abs(C*(dist[i,2]^Theta)- dist[i,3] ) +1 , 10);
}
text(log(dist$NodeCount,10), log(dist$EdgeCount,10), labels = sprintf("%.2f",dist$outlierscore) , adj = 1, cex = 0.5)
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("purchase_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(fit$coefficients[1], fit$coefficients[2]) 
savehistory("powerlaw_SimScore_nodes_edges_purchase.R")
# ref for line fitting: http://www.theanalysisfactor.com/linear-models-r-plotting-regression-lines/
C = 10^fit$coefficients[1]
Theta = fit$coefficients[2]
dist$outlierscore = rep(0,dim(dist)[1])
for(i in 1:dim(dist)[1]){
dist$outlierscore[i] = max(C*(dist[i,2]^Theta), dist[i,3]  ) / min(C*(dist[i,2]^Theta), dist[i,3] ) * log(abs(C*(dist[i,2]^Theta)- dist[i,3] ) +1 , 10);
}
text(log(dist$NodeCount,10), log(dist$EdgeCount,10), labels = sprintf("%.2f",dist$outlierscore) , adj = 1, cex = 0.5)
fit
library(ggplot2)
library(ggrepel)
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(fit$coefficients[1], fit$coefficients[2])
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(data=NULL,fit$coefficients[1], fit$coefficients[2])
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=fit$coefficients[1], slope=fit$coefficients[2])
ggplot(data = dist, aes(x = NodeCount, y = EdgeCount)) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=C, slope=Theta)
ggplot(data = dist, aes(x = NodeCount^Theta, y = EdgeCount)) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=C, slope=Theta)
ggplot(data = dist, aes(x = NodeCount, y = EdgeCount)) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=0, slope=Theta)
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("purchase_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(fit$coefficients[1], fit$coefficients[2]) 
savehistory("powerlaw_SimScore_nodes_edges_purchase.R")
# ref for line fitting: http://www.theanalysisfactor.com/linear-models-r-plotting-regression-lines/
C = 10^fit$coefficients[1]
Theta = fit$coefficients[2]
dist$outlierscore = rep(0,dim(dist)[1])
for(i in 1:dim(dist)[1]){
dist$outlierscore[i] = max(C*(dist[i,2]^Theta), dist[i,3]  ) / min(C*(dist[i,2]^Theta), dist[i,3] ) * log(abs(C*(dist[i,2]^Theta)- dist[i,3] ) +1 , 10);
}
text(log(dist$NodeCount,10), log(dist$EdgeCount,10), labels = sprintf("%.2f",dist$outlierscore) , adj = 1, cex = 0.5)
library(ggplot2)
library(ggrepel)
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=fit$coefficients[1], slope=fit$coefficients[2])
write.csv(dist,file="purchase_sim_outlierscores.csv")
d2 = read.csv(file="purchase_sim_outlierscores_withlof.csv", h=T)
str(d2)
d2 = read.csv(file="purchase_sim_outlierscores_withlof.csv", h=F)
str(dist)
str(d2)
d2$V7 = d2$V6+d2$V5
d2 = read.csv(file="purchase_sim_outlierscores_withlof.csv", h=F)
d2$V7 = d2$V6+d2$V5
dist=d2
ggplot(data = dist, aes(x = log(V3,10), y = log(V4,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$V5)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=fit$coefficients[1], slope=fit$coefficients[2])
d2 = read.csv(file="purchase_sim_outlierscores_withlof.csv", h=F)
d2$V7 = d2$V6+d2$V5
dist=d2
ggplot(data = dist, aes(x = log(V3,10), y = log(V4,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$V7)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=fit$coefficients[1], slope=fit$coefficients[2])
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("sale_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(fit$coefficients[1], fit$coefficients[2])
savehistory("powerlaw_SimScore_nodes_edges_sale.R")
C = 10^fit$coefficients[1]
Theta = fit$coefficients[2]
dist$outlierscore = rep(0,dim(dist)[1])
for(i in 1:dim(dist)[1])
{
dist$outlierscore[i] = max(C*(dist[i,2]^Theta), dist[i,3]  ) / min(C*(dist[i,2]^Theta), dist[i,3] ) * log(abs(C*(dist[i,2]^Theta)- dist[i,3] ) +1 , 10);
}
text(log(dist$NodeCount,10), log(dist$EdgeCount,10), labels = sprintf("%.2f",dist$outlierscore) , adj = 1, cex = 0.5)
library(ggplot2)
library(ggrepel)
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=fit$coefficients[1], slope=fit$coefficients[2])
write.csv(dist,file="sale_sim_outlierscores.csv")
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("purchase_LCS_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(fit$coefficients[1], fit$coefficients[2])
savehistory("powerlaw_LCSScore_nodes_edges_purchase.R")
C = 10^fit$coefficients[1]
Theta = fit$coefficients[2]
dist$outlierscore = rep(0,dim(dist)[1])
for(i in 1:dim(dist)[1])
{
dist$outlierscore[i] = max(C*(dist[i,2]^Theta), dist[i,3]  ) / min(C*(dist[i,2]^Theta), dist[i,3] ) * log(abs(C*(dist[i,2]^Theta)- dist[i,3] ) +1 , 10);
}
text(log(dist$NodeCount,10), log(dist$EdgeCount,10), labels = sprintf("%.2f",dist$outlierscore) , adj = 1, cex = 0.5)
library(ggplot2)
library(ggrepel)
ggplot(data = dist, aes(x = log(NodeCount,10), y = log(EdgeCount,10))) + 
    geom_text_repel(aes(label = sprintf("%.2f",dist$outlierscore)), 
       box.padding = unit(0.45, "lines")) +
    geom_point(colour = "green", size = 3)+geom_abline(intercept=fit$coefficients[1], slope=fit$coefficients[2])
write.csv(dist,file="purchase_LCS_outlierscores.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(fit$coefficients[1], fit$coefficients[2])
setwd("C:/Users/samee/Documents/CS584/code_javaSE/project")
dist = read.csv("sale_LCS_Egonets_NodeAndEdge_Count.csv")
str(dist)
fit = lm( log(dist$EdgeCount,10) ~ log(dist$NodeCount,10))
fit
plot(y = log(dist$EdgeCount,10), x = log(dist$NodeCount,10), pch=4, col= "red")
abline(fit$coefficients[1], fit$coefficients[2])
savehistory("powerlaw_LCSScore_nodes_edges_sale.R")
