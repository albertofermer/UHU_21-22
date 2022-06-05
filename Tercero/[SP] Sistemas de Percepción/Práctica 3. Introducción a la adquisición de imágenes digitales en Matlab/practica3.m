%% Práctica 3: Adquisición de Imágenes

%% Ejercicio 1. 
% Visualiza los píxeles con una intensidad mayor a 70, 140 y 210 
% respectivamente.

datos=imaqhwinfo('winvideo');
video=videoinput('winvideo',1,'MJPG_640x480'); %
preview(video)
original = getsnapshot(video); original = imresize(original,0.5);
imshow(original); hold on

umbrales = [70, 140, 210];

Iintensidad = uint8(mean(original,3));
imshow(Iintensidad);

Imod70 = funcion_visualiza(original,Iintensidad>70,[0,255,0],false);
Imod140 = funcion_visualiza(original,Iintensidad>140,[0,255,0],false);
Imod210 = funcion_visualiza(original,Iintensidad>210,[0,255,0],false);

subplot(1,4,1), imshow(original);
subplot(1,4,2), imshow(Imod70);
subplot(1,4,3), imshow(Imod140);
subplot(1,4,4), imshow(Imod210);

%% Ejercicio 2.
% Para cada una de las imágenes, visualizar las 5 agrupaciones mayores de 
% píxeles conectados. Localizar las agrupaciones por su centroide y marcar
% el centroide de la agrupación mayor en otro color.

imshow(Imod70);

[Ietiq,N] = bwlabel(Iintensidad>70);
stats = regionprops((Ietiq),'Area','Centroid');

    centroides = cat(1,stats.Centroid);

    [~,pos] = max(cat(1,stats.Area));
    [~,sortedPosArea] = sort(cat(1,stats.Area),'descend');
    area5 = sortedAreas(5);
    sortedPosArea = sortedPosArea(1:5);
    sortedAreas = sortedAreas(1:5);
    Iaux = original;

    for i=1:5
    IetiqAux = IetiqAux + (Ietiq == sortedPosArea(i));
    end

    x = centroides(sortedPosArea(1:5),1);
    y = centroides(sortedPosArea(1:5),2);
    Iaux = funcion_visualiza(Iaux,IetiqAux,[0,255,0],false);
    imshow(Iaux), hold on;
    plot(x,y,'*r');
    plot(centroides(sortedPosArea(1),1),centroides(sortedPosArea(1),2),'*b');

flushdata(video)

%% Ejercicio 3.
    % Visualiza la escena inicialmente oscurecida y aclarándose
    % progresivamente hasta gamma = 4.

    clc
    clear all

    datos=imaqhwinfo('winvideo');
    video=videoinput('winvideo',1,'MJPG_640x480'); %
    video.TriggerRepeat = inf;
    video.FramesPerTrigger = 3;
    video.FrameGrabInterval = 3;
    
    start(video);

    for gamma=10:-0.05:0
    
        I = getdata(video,1); I = imresize(I,0.5);
        Ib = imadjust(I,[],[],gamma);
        imshow([I Ib]);
    end

    stop(video);

%% Ejercicio 4.
%Visualiza todos los píxeles con una intensidad mayor que un determinado
%umbral [0-255]

    clc
    clear all

    datos = imaqhwinfo('winvideo');
    video = videoinput('winvideo',1,'MJPG_640x480');
    video.TriggerRepeat = inf;
    video.FramesPerTrigger = 3;
    video.FrameGrabInterval = 3;
    video.ReturnedColorSpace = 'grayscale';

    start(video);

    for umbral=0:255
        I = getdata(video,1);

        Ib = funcion_visualiza(I,I>umbral,[255,0,0],false);
        imshow([I Ib(:,:,1)])
    end

    stop(video);

    %% Ejercicio 5.1. 
    % Diferencias entre los distintos frames de intensidad que captura 
    % la webcam.
    clc
    clear all

    imaqmex('feature','-limitPhysicalMemoryUsage',false);
    datos = imaqhwinfo('winvideo');
    video = videoinput('winvideo',1,'MJPG_640x480');
    video.TriggerRepeat = inf;
    video.FramesPerTrigger = 3;
    video.FrameGrabInterval = 3;
    video.ReturnedColorSpace = 'grayscale';

    start(video);
    anterior = getdata(video,1);anterior = imresize(anterior,0.5);
    while(video.FramesAcquired<100)
        actual = getdata(video,1); actual = imresize(actual,0.5);
        Ib = imabsdiff(actual,anterior);
        anterior = actual;
        imshow([Ib])
    end

    stop(video);
    flushdata(video);

    %% 5.2
    % Píxeles cuya diferencia de intensidad sea mayor de 100.
    clc
    clear all

    imaqmex('feature','-limitPhysicalMemoryUsage',false);
    datos = imaqhwinfo('winvideo');
    video = videoinput('winvideo',1,'MJPG_640x480');
    video.TriggerRepeat = inf;
    video.FramesPerTrigger = 3;
    video.FrameGrabInterval = 3;
    video.ReturnedColorSpace = 'grayscale';

    start(video);
    anterior = getdata(video,1);anterior = imresize(anterior,0.5);
    while(true)
        actual = getdata(video,1); actual = imresize(actual,0.5);
        Ib = funcion_visualiza(actual,imabsdiff(actual,anterior)>25,[0,255,0],false);
        anterior = actual;
        imshow([Ib])
    end

    stop(video);
    flushdata(video);

    %% Detectar centroide de la mayor agrupación de píxeles
    clc
    clear all

    imaqmex('feature','-limitPhysicalMemoryUsage',false);
    datos = imaqhwinfo('winvideo');
    video = videoinput('winvideo',1,'MJPG_640x480');
    video.TriggerRepeat = inf;
    video.FramesPerTrigger = 3;
    video.FrameGrabInterval = 3;
    video.ReturnedColorSpace = 'grayscale';

    start(video);
    anterior = getdata(video,1);anterior = imresize(anterior,0.5);
    while(video.FramesAcquired<150)
        actual = getdata(video,1); actual = imresize(actual,0.5);
        %Ib = funcion_visualiza(actual,imabsdiff(actual,anterior)>25,[0,255,0],false);
        [Ietiq,N] = bwlabel(imabsdiff(actual,anterior)>75);
        stats = regionprops(Ietiq,'Area','Centroid');
        anterior = actual;
        areas = cat(1,stats.Area); centroids = cat(1,stats.Centroid);

        [~,sortedAreaPos] = sort(areas,'descend');
        
        imshow([Ietiq actual]), hold on;
        if(size(areas) > 0)
            plot(centroids(sortedAreaPos(1),1),centroids(sortedAreaPos(1),2),'+r');
        end
    end

    stop(video);
    flushdata(video);
