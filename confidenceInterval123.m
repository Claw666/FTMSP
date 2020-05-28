phoneCalls = [1, 0, 700; 
    1, 0, 400; 
    1, 0, 200; 
    2, 0, 700; 
    2, 0, 400; 
    2, 0, 100; 
    1, 200, 500; 
    1, 400, 700;
    2, 50, 376]

amountOfCalls = size(phoneCalls,1);
infoTable = zeros(amountOfCalls,6);
countType1 = 0;
countType2 = 0;


for i = 1:amountOfCalls
   
    if(phoneCalls(i,1) == 1)
        countType1 = countType1 + 1;
       
    elseif(phoneCalls(i,1) == 2)
        countType2 = countType2 + 1;
       
    end
        
    infoTable(i,1) =  phoneCalls(i,1);
    infoTable(i,2) =  phoneCalls(i,2);
    infoTable(i,3) = phoneCalls(i,3);
    infoTable(i,4) = phoneCalls(i,3) - phoneCalls(i,2);
    
end


fprintf('General Table containing type 1 and 2')
display(infoTable);

type1Index = 1;
type2Index = 1;
infoTable1 = zeros(countType1 , 5);
infoTable2 = zeros(countType2 , 5);

for i = 1:amountOfCalls
    
   if(infoTable(i,1) == 1)
       
      infoTable1(type1Index,1) = 1;
      infoTable1(type1Index,2) = infoTable(i,2);
      infoTable1(type1Index,3) = infoTable(i,3);
      infoTable1(type1Index,4) = infoTable(i,4);
      
      type1Index = type1Index + 1;
   elseif(infoTable(i,1) == 2)
       
      infoTable2(type2Index,1) = 2;
      infoTable2(type2Index,2) = infoTable(i,2);
      infoTable2(type2Index,3) = infoTable(i,3);
      infoTable2(type2Index,4) = infoTable(i,4);
      
      type2Index = type2Index + 1;
      
   end
    
end

fprintf('Separated table of type 1');
display(infoTable1);

fprintf('Separated table of type 2');
display(infoTable2);

%%%%%%START CI

varGeneral = 0;
varType1 = 0;
varType2 = 0;
meanGeneral = 0;
meanType1 = 0;
meanType2 = 0;


for i = 1:amountOfCalls
   
    meanGeneral = meanGeneral + infoTable(i,4);
    
    if(infoTable(i,1) == 1)
        meanType1 = meanType1 + infoTable(i,4);
        
    else
        meanType2 = meanType2 + infoTable(i,4);
    
    
    end

end

meanGeneral = meanGeneral/amountOfCalls;
meanType1 = meanType1/countType1;
meanType1 = meanType1/countType2;

fprintf('mean general: ')
display(meanGeneral);
fprintf('mean type1: ')
display(meanType1);
fprintf('mean type2: ')
display(meanType2);


for i = 1:amountOfCalls
   
    varnGeneral = varGeneral + (infoTable(i,4) - meanGeneral)^2;
    
    if(infoTable(i,1) == 1)
        varType1 = varType1 + (infoTable(i,4) - meanType1)^2;
       
    else
        varType2 = varType2 + (infoTable(i,4) - meanType2)^2;
    
    end

end


varGeneral = varGeneral/(amountOfCalls - 1);
varType1 = varType1/(countType1 - 1);
varType2 = varType2/(countType2 - 1);

fprintf('var general: ')
display(varGeneral);
fprintf('var type1: ')
display(varType1);
fprintf('var type2: ')
display(varType2);

stdGeneral = sqrt(varGeneral);
stdType1 = sqrt(varType1);
stdType2 = sqrt(varType2);

tsGeneral = tinv([0.025  0.975], amountOfCalls - 1);
tsType1 = tinv([0.025  0.975], countType1 - 1);
tsType2 = tinv([0.025  0.975], countType2 - 1);

CIgeneral = meanGeneral + tsGeneral * stdGeneral;
CItype1 = meanType1 + tsType1 * stdType1;
CItype2 = meanType2 + tsType2 * stdType2;

fprintf('Confidence Interval General');
display(CIgeneral);

fprintf('Confidence Interval Type1');
display(CItype1)

fprintf('Confidence Interval Type2');
display(CItype2)


minTime = min(infoTable(:,4)); %Trying to figure out how to plot the C.I.
maxTime = max(infoTable(:,4));

figure
hold on;
plot(infoTable(:,4),meanGeneral,'x');


%$standardErrorMean = stdGeneral/sqrt(amountOfCalls);


%ci95 = T_mult * stdGeneral/sqrt(amountOfCalls);
%h = figure('Color', [1 1 1]);
%p1 = plot(meanGeneral);
%set(p1, 'LineWidth', 2, 'MarkerSize', 5, 'Marker', 'x')

%hold on

%e1 = errorbar(meanGeneral, ci95);
%set(e1, 'LineStyle', 'none');

%title('Plot with 95% confidence interval')



