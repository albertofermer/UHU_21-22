function h = funcion_imhistv2(img)
    h = zeros(256,1);
    for i=1:256
    h(i) = sum(sum((img == (i-1))));
    end
end