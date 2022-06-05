function var = funcion_imabsdiff(img1,img2)
    var = abs((double(img1)-double(img2)));
    var = uint8(var);
end
