callsAllRuns = zeros( (size(callInformation0,1) + size(callInformation1,1) + size(callInformation2,1) + size(callInformation3,1) + size(callInformation4,1)+ size(callInformation5,1)+ size(callInformation6,1)+ size(callInformation7,1)+ size(callInformation8,1)+ size(callInformation9,1)), 5);
%  callInformation0 = table2array(callInformation0);
%  callInformation1 = table2array(callInformation1);
% callInformation2 = table2array(callInformation2);
% callInformation3 = table2array(callInformation3);
% callInformation4 = table2array(callInformation4);
% callInformation5 = table2array(callInformation5);
% callInformation6 = table2array(callInformation6);
% callInformation7 = table2array(callInformation7);
% callInformation8 = table2array(callInformation8);
% callInformation9 = table2array(callInformation9);
% 
for i=1:1:(size(callInformation0,1) + size(callInformation1,1) + size(callInformation2,1) + size(callInformation3,1) + size(callInformation4,1))
    if (i < (size(callInformation0,1)))
        callsAllRuns(i,1) = callInformation0(i,1);
        callsAllRuns(i,2) = callInformation0(i,2);
        callsAllRuns(i,3) = callInformation0(i,3);
        callsAllRuns(i,4) = callInformation0(i,4);
        callsAllRuns(i,5) = callInformation0(i,5);
    else
        if (i < (size(callInformation1,1)))
        callsAllRuns(i,1) = callInformation1(i,1);
        callsAllRuns(i,2) = callInformation1(i,2);
        callsAllRuns(i,3) = callInformation1(i,3);
        callsAllRuns(i,4) = callInformation1(i,4);
        callsAllRuns(i,5) = callInformation1(i,5);
        else
            if (i < (size(callInformation2,1)))
            callsAllRuns(i,1) = callInformation2(i,1);
            callsAllRuns(i,2) = callInformation2(i,2);
            callsAllRuns(i,3) = callInformation2(i,3);
            callsAllRuns(i,4) = callInformation2(i,4);
            callsAllRuns(i,5) = callInformation2(i,5);
             else
                 if (i < (size(callInformation3,1)))
                callsAllRuns(i,1) = callInformation3(i,1);
                callsAllRuns(i,2) = callInformation3(i,2);
                callsAllRuns(i,3) = callInformation3(i,3);
                callsAllRuns(i,4) = callInformation3(i,4);
                callsAllRuns(i,5) = callInformation3(i,5);
                 else
                     if (i < (size(callInformation4,1)))
                    callsAllRuns(i,1) = callInformation4(i,1);
                    callsAllRuns(i,2) = callInformation4(i,2);
                    callsAllRuns(i,3) = callInformation4(i,3);
                    callsAllRuns(i,4) = callInformation4(i,4);
                    callsAllRuns(i,5) = callInformation4(i,5);
                     else
                         if (i < (size(callInformation5,1)))
                        callsAllRuns(i,1) = callInformation5(i,1);
                        callsAllRuns(i,2) = callInformation5(i,2);
                        callsAllRuns(i,3) = callInformation5(i,3);
                        callsAllRuns(i,4) = callInformation5(i,4);
                        callsAllRuns(i,5) = callInformation5(i,5);
                         else
                            if (i < (size(callInformation6,1)))
                            callsAllRuns(i,1) = callInformation6(i,1);
                            callsAllRuns(i,2) = callInformation6(i,2);
                            callsAllRuns(i,3) = callInformation6(i,3);
                            callsAllRuns(i,4) = callInformation6(i,4);
                            callsAllRuns(i,5) = callInformation6(i,5);
                 else
                         if (i < (size(callInformation7,1)))
                        callsAllRuns(i,1) = callInformation7(i,1);
                        callsAllRuns(i,2) = callInformation7(i,2);
                        callsAllRuns(i,3) = callInformation7(i,3);
                        callsAllRuns(i,4) = callInformation7(i,4);
                        callsAllRuns(i,5) = callInformation7(i,5);
                 else
                         if (i < (size(callInformation8,1)))
                        callsAllRuns(i,1) = callInformation8(i,1);
                        callsAllRuns(i,2) = callInformation8(i,2);
                        callsAllRuns(i,3) = callInformation8(i,3);
                        callsAllRuns(i,4) = callInformation8(i,4);
                        callsAllRuns(i,5) = callInformation8(i,5);
                 
                    else
                         if (i < (size(callInformation9,1)))
                        callsAllRuns(i,1) = callInformation9(i,1);
                        callsAllRuns(i,2) = callInformation9(i,2);
                        callsAllRuns(i,3) = callInformation9(i,3);
                        callsAllRuns(i,4) = callInformation9(i,4);
                        callsAllRuns(i,5) = callInformation9(i,5);
                         end
                         end
                         end
                            end
                         end
                         
                     end
                end
             end
        end
    end
end

phoneCalls = zeros (size(callsAllRuns,1), 3);
phoneCalls(:,1) = callsAllRuns(:,1);
phoneCalls(:,2) = callsAllRuns(:,3);
phoneCalls(:,3) = callsAllRuns(:,5);

checkRequirementsPercentage(phoneCalls)

violinTable = zeros(size(phoneCalls,1), 2);
for i=1:size(phoneCalls,1)
    violinTable(i,1) = phoneCalls(i,2) - phoneCalls(i,3);
    violinTable(i,1) = phoneCalls(i,1);
end
fprintf('General Table containing type 1 and 2 calls')
display(infoTable);


figure
vs = violinplot(infoTable(:,4), infoTable(:,1));
ylabel('Average time')
xlabel('Call type')

