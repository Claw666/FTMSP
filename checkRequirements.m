function [over5, over10, over3, over7] = checkRequirements(phonecalls)

% Function that takes in phonecalls matrix
% phonecalls structure: [type of call (0 / 1) , start (s), end (s)]
    over5 = 0;
    over10 = 0;
    over3 = 0;
    over7 = 0;
    consumercalls = 0;
    corporatecalls = 0;
    [amountofcalls, m] = size(phonecalls)
    
    for i=1:1:amountofcalls
        if phonecalls(i,1) == 1 % chekc if call is consumer
            consumercalls = consumercalls +1
            if phonecalls(i,3) - phonecalls(i,2) > 600
                over10 = over10 +1 %if longer than 10min -> +1
            else
                if phonecalls(i,3) - phonecalls(i,2) > 300
                    over5 = over5 +1 %if longer than 5min -> +1
                end
            end
        else %else it's corporate
            corporatecalls = corporatecalls + 1
            if phonecalls(i,3) - phonecalls(i,2) > 420 %blaze it
                over7 = over7 + 1
            else 
                if phonecalls(i,3) - phonecalls(i,2) > 180
                    over3 = over3 + 1 
                end
            end
        end
    end
end
