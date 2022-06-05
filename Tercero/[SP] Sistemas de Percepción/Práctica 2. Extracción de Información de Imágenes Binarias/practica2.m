%%  Práctica 2.

%% Ejercicio 1.
%   Lee la imagen "ImagenBinaria.tif"
    Ib = imread("ImagenBinaria.tif");

%% Ejercicio 2.
%   Genera una matriz binaria donde el valor 0 significe píxel de fondo y
%   el 1 píxel de objeto.
    ImagenBinaria = (Ib ~= 0);

%% Ejercicio 3.
%   Genera una imagen en color donde se visualicen los objetos presentes en
%   la imagen.

    [Ietiq,N] = etiquetar_imagen(ImagenBinaria);
    Io = uint8(zeros(size(Ietiq)));
    for i=10:-1:1
    Io = Io + uint8(funcion_visualiza(ImagenBinaria,Ietiq == i,[255*rand(),255*rand(),255*rand()],false)); 
    end
    imshow([cat(3,Ib,Ib,Ib)  Io]);


%% Ejercicio 4. 
% Generar una imagen donde se visualicen los centroides 
% de los objetos de menor y mayor área.

    areas = calcula_areas(Ietiq,N);
    centroides = calcula_centroides(Ietiq,N);
    [SortedAreas,pos] = sort(areas);
    areaMax = pos(end);
    areaMin = pos(1);

    % Muestra los centroides de los objetos con mayor y menor área
    imshow(Ietiq); hold on;
    plot(centroides(areaMin,1),centroides(areaMin,2),'.r');
    plot(centroides(areaMax,1),centroides(areaMax,2),'.r');

    %Pinta las figuras con mayor y menor área.
    funcion_visualiza(ImagenBinaria,Ietiq == areaMax | Ietiq == areaMin, [0,255,255],true);

%% Ejercicio 5. 
% Generar una imagen donde sólo se muestren los dos objetos de mayor área.
    Ifiltrada = filtra_objetos(ImagenBinaria,SortedAreas(end-1));
    imshow(Ifiltrada);


