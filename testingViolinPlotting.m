
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


for i = 1:amountOfCalls
     
    infoTable(i,1) =  phoneCalls(i,1);
    infoTable(i,2) =  phoneCalls(i,2);
    infoTable(i,3) = phoneCalls(i,3);
    infoTable(i,4) = phoneCalls(i,3) - phoneCalls(i,2);
    
end


fprintf('General Table containing type 1 and 2 calls')
display(infoTable);


figure
vs = violinplot(infoTable(:,4), infoTable(:,1));
ylabel('Average time')
xlabel('Call type')


