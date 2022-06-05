%%
% Ejercicio 1.
%
inf_img = imfinfo("P1_1.jpg");
nf = inf_img.Height;
nc = inf_img.Width;
bitDepth = inf_img.BitDepth;
color = logical([]);
if( inf_img.NumberOfSamples == 3) 
    color = true;
end

%% Ejercicio 6.
imagen = imread("P1_1.jpg");
imagen2 = 255 - imagen;
imshow(imagen2);
imwrite(imagen2,"imagen_complementaria.jpg");
%
R = imagen(:,:,1);
G = imagen(:,:,2);
B = imagen(:,:,3);
im_c = cat(3,R,G,B); %Junta las 3 imagenes
im_c2 = [R,G,B]; %%Genera 3 imagenes
imshow(im_c);
%% Ejercicio 7
imagen3 = imagen(:,:,1);
imshow(imagen3);

%%  Ejercicio 8

%addpath("../Pr�ctica 0. Programaci�n B�sica en MATLAB/Funciones");
gamma = 0.5;
imagen4 = imadjust(imagen3,[],[],gamma);
gamma = 1.5;
imagen5 = imadjust(imagen3,[],[],gamma);
ImagenSalida = [imagen4,imagen5];

imshow(ImagenSalida);
imshow(imagen5);

%%Ejercicio 9

imagen6 = imabsdiff(imagen4,imagen5);
imshow(imagen6);

imagen_ml = funcion_imabsdiff(imagen5,imagen4);

funcion_compara_matrices(imagen_ml,imagen6);

%% Ejercicio 10

G = imagen(:,:,2);
figure, imhist(G);
h = imhist(G);
X = 104;
h(X+1)

v1 = funcion_imhist(G);
v2 =  funcion_imhistv2(G);
v1
h
funcion_compara_matrices(v2,h);
funcion_compara_matrices(v1,h);
