callsAllRuns = zeros( (size(callInformation0,1) + size(callInformation1,1) + size(callInformation2,1) + size(callInformation3,1) + size(callInformation4,1)), 5);
%callInformation0 = table2array(callInformation0);
%callInformation1 = table2array(callInformation1);
%callInformation2 = table2array(callInformation2);
%callInformation3 = table2array(callInformation3);
%callInformation4 = table2array(callInformation4);

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
                 else if (i < (size(callInformation4,1)))
                    callsAllRuns(i,1) = callInformation4(i,1);
                    callsAllRuns(i,2) = callInformation4(i,2);
                    callsAllRuns(i,3) = callInformation4(i,3);
                    callsAllRuns(i,4) = callInformation4(i,4);
                    callsAllRuns(i,5) = callInformation4(i,5);
                    
                     end
                end
             end
        end
    end
end

check