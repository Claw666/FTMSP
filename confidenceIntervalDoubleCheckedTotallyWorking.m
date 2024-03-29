function [CIgeneral, CItype1, CItype2] = getConfidenceInterval(phoneCalls)

%phoneCalls = [1, 0, 700; 
%    1, 0, 400; 
%    1, 0, 200; 
%    0, 0, 700; 
%    0, 0, 400; 
%    0, 0, 100; 
%    1, 200, 500; 
%    1, 400, 700;
%    0, 50, 376;
%    1, 3, 534;
%    0, 575, 2342;
%    1 4 19;
%    0 23 225;
%    ]

amountOfCalls = size(phoneCalls,1);
infoTable = zeros(amountOfCalls,6);
countType0 = 0;
countType1 = 0;


for i = 1:amountOfCalls
   
    if(phoneCalls(i,1) == 0)
        countType0 = countType0 + 1;
       
    elseif(phoneCalls(i,1) == 1)
        countType1 = countType1 + 1;
       
    end
        
    infoTable(i,1) =  phoneCalls(i,1);
    infoTable(i,2) =  phoneCalls(i,2);
    infoTable(i,3) = phoneCalls(i,3);
    infoTable(i,4) = phoneCalls(i,3) - phoneCalls(i,2);
    
end


fprintf('General Table containing type 0 and 1')
display(infoTable);

type0Index = 1;
type1Index = 1;
infoTable0 = zeros(countType0 , 5);
infoTable1 = zeros(countType1 , 5);

for i = 1:amountOfCalls
    
   if(infoTable(i,1) == 0)
       
      infoTable0(type0Index,1) = 0;
      infoTable0(type0Index,2) = infoTable(i,2);
      infoTable0(type0Index,3) = infoTable(i,3);
      infoTable0(type0Index,4) = infoTable(i,4);
      
      type0Index = type0Index + 1;
   elseif(infoTable(i,1) == 1)
       
      infoTable1(type1Index,1) = 1;
      infoTable1(type1Index,2) = infoTable(i,2);
      infoTable1(type1Index,3) = infoTable(i,3);
      infoTable1(type1Index,4) = infoTable(i,4);
      
      type1Index = type1Index + 1;
      
   end
    
end

fprintf('Separated table of call type 0');
display(infoTable0);

fprintf('Separated table of call type 1');
display(infoTable1);

%%%%%%START CI

varGeneral = 0;
varType0 = 0;
varType1 = 0;
meanGeneral = 0;
meanType0 = 0;
meanType1 = 0;


for i = 1:amountOfCalls
   
    meanGeneral = meanGeneral + infoTable(i,4);
    
    if(infoTable(i,1) == 1)
        meanType0 = meanType0 + infoTable(i,4);
        
    else
        meanType1 = meanType1 + infoTable(i,4);
    
    
    end

end

meanGeneral = meanGeneral/amountOfCalls;
meanType0 = meanType0/countType0;
meanType1 = meanType1/countType1;

fprintf('mean general: ')
display(meanGeneral);
fprintf('mean type0: ')
display(meanType0);
fprintf('mean type1: ')
display(meanType1);


for i = 1:amountOfCalls
   
    varGeneral = varGeneral + (infoTable(i,4) - meanGeneral)^2;
    
    if(infoTable(i,1) == 1)
        varType0 = varType0 + (infoTable(i,4) - meanType0)^2;
       
    else
        varType1 = varType1 + (infoTable(i,4) - meanType1)^2;
    
    end

end


varGeneral = varGeneral/(amountOfCalls - 1);
varType0 = varType0/(countType0 - 1);
varType1 = varType1/(countType1 - 1);

fprintf('var general: ')
display(varGeneral);
fprintf('var type1: ')
display(varType0);
fprintf('var type2: ')
display(varType1);

stdGeneral = sqrt(varGeneral);
stdType0 = sqrt(varType0);
stdType1 = sqrt(varType1);

stdErrorGeneral = stdGeneral/sqrt(amountOfCalls);
stdErrorType0 = stdType0/sqrt(countType0);
stdErrorType1 = stdType1/sqrt(countType1);


tsGeneral = tinv([0.025  0.975], amountOfCalls - 1);
tsType0 = tinv([0.025  0.975], countType0 - 1);
tsType1 = tinv([0.025  0.975], countType1 - 1);

CIgeneral = meanGeneral + tsGeneral * stdErrorGeneral;
CItype0 = meanType0 + tsType0 * stdErrorType0;
CItype1 = meanType1 + tsType1 * stdErrorType1;


fprintf('Confidence Interval General');
display(CIgeneral);

fprintf('Confidence Interval Call Type0');
display(CItype0)

fprintf('Confidence Interval Call Type1');
display(CItype1)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%just for testing the corectness - The formula is correct
%[h,p,ci,stats] = ttest(infoTable(:,4), 0 ,'Alpha', 0.05);
%%%%%%%%%%%%%%%%%%%TO BE DELETED%%%%%%%%%%%%%%%

%A histogram lotting probably won't be very useful 
figure(1)
clf
hist(infoTable(:,4),60)  %50 bins
title('60 batches');
ylabel('Time')
%End Hist Plotting





