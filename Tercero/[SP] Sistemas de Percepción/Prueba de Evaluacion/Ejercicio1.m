
%% Ejercicio 1.
addpath('./Funciones');
load('./Variables Requeridas/parametros_clasificador.mat');
video = VideoReader('./Variables Requeridas/video_entrada.avi');
get(video);

output = VideoWriter('./Variables Generadas/video_salida_Ejercicio1.avi', 'Uncompressed AVI');
output.FrameRate = video.FrameRate;
open(output);

video.CurrentTime = 0;
for i=1:video.NumFrames

    I = readFrame(video);
    Ib = filtra_objetos(calcula_deteccion_multiples_esferas_en_imagen(I,datosMultiplesEsferas_clasificador(:,4),datosMultiplesEsferas_clasificador(:,1:3)),numPix);
    [Ietiq,N] = etiquetar_imagen(Ib);
    
    areas = calcula_areas(Ietiq,N);
    centroides = calcula_centroides(Ietiq,N);
    
    if(~isempty(centroides))
        [~,pos] = min(areas);
        x = round(centroides(pos,2));
        y = round(centroides(pos,1));

        Ibcent = zeros(size(I,1),size(I,2));
        Ibcent(x-1:x+1,y-1:y+1) = 1;

        Io = funcion_visualiza(I,Ibcent & Ietiq == N(pos),[0 0 255], false);
        %Io = funcion_visualiza(I,(Ietiq == N(pos)),[255 0 0], false);
    else
        Ibcent = zeros(size(I,1),size(I,2));
        Ibcent(1:3,1:3) = 1;
        Io = funcion_visualiza(I,Ibcent & Ietiq == N(pos),[0 0 255], false);
        
    end
    imshow(Io);
    
    writeVideo(output,Io);
    
end

close(output);

rmpath('./Funciones');