x = 1:100;
y = randn(50,100);

N = size(y,1);
yMean = mean(y);
ySEM = std(y)/sqrt(N);

CI95 = tinv([0.025 0.975],N-1);
yCI95 = bsxfun(@times,ySEM,CI95(:));

figure
plot(x, yMean)
hold on
plot(x,yCI95+yMean)
hold off
grid