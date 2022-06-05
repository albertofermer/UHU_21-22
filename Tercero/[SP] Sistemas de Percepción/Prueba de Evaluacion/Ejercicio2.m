
%% Ejercicio 2.
addpath('./Funciones');
load('./Variables Requeridas/parametros_clasificador.mat');
video = VideoReader('./Variables Requeridas/video_entrada.avi');
get(video);

output = VideoWriter('./Variables Generadas/video_salida_Ejercicio2.avi', 'Uncompressed AVI');
output.FrameRate = video.FrameRate;
open(output);

video.CurrentTime = 0;

Ianterior = readFrame(video);
IintensidadAnterior = uint8(mean(Ianterior,3));
for i=2:video.NumFrames
I = readFrame(video);

% Representa los píxeles con una Intensidad entre 85 y 115:
Iintensidad = uint8(mean(I,3));
Io = funcion_visualiza(I,Iintensidad > 85 & Iintensidad < 115,[255,0,0], false);

% Representa los píxeles que sean del color del seguimiento Y la diferencia
% entre sus imágenes de intensidad sea mayor a 30.
    
IbColor = filtra_objetos(calcula_deteccion_multiples_esferas_en_imagen(I,datosMultiplesEsferas_clasificador(:,4),datosMultiplesEsferas_clasificador(:,1:3)),numPix);

IbIntensidad = imabsdiff(Iintensidad,IintensidadAnterior);
Io2 = funcion_visualiza(Io,IbIntensidad > 30 & IbColor,[0,255,0], false);

% Representa los píxeles que cumplen las dos condiciones anteriores:
Io3 = funcion_visualiza(Io2,(IbIntensidad > 30 & IbColor) & (Iintensidad > 85 & Iintensidad < 115),[0,0,255], false);
imshow(Io3);

IintensidadAnterior = Iintensidad;
writeVideo(output,Io3);
end
close(output);

rmpath('./Funciones');
