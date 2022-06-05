function h = funcion_imhist(img)
    [nf,nc] = size(img);
    h = zeros(256,1);
    for i = 1:nf
        for j = 1:nc
            h(double(img(i,j)+1)) = h(double(img(i,j)+1)) + 1;

        end
    end

    h = uint8(h);
end