clear all
clc

% Función que devuelve una estructura con información del hardware de adquisición de imágenes
% disponible, incluyendo los adaptadores de video instalados
datos=imaqhwinfo;

%       InstalledAdaptors: {'coreco' 'demo' 'winvideo'}
%       MATLABVersion: '7.4 (R2007a)'
%       ToolboxName: 'Image Acquisition Toolbox'
%       ToolboxVersion: '2.1 (R2007a)'

%Función que devuelve una estructura con información del dispositivo de video instalado
datos=imaqhwinfo('winvideo');

%       DefaultFormat: 'RGB24_352x288'
%       DeviceFileSupported: 0
%       DeviceName: 'ICatch (VI) PC Camera'
%       DeviceID: 1
%       ObjectConstructor: 'videoinput('winvideo', 1)'
%       SupportedFormats: {1x10 cell}

%
% SupportedFormats
%
% 'I420_160x120' 'I420_176x144' 'I420_320x240' 'I420_352x288' 'I420_640x480'
%
% 'RGB24_160x120' 'RGB24_176x144' 'RGB24_320x240' 'RGB24_352x288' 'RGB24_640x480'

% Función para crear el objeto de video que contiene la configuración del
% dispositivo de adquisición de imágenes (WebCam, cámara...) y
% con el que Matlab se comunicará con
% el dispositivo de adquisición de imágenes (Webcam, cámara,...)

video=videoinput('winvideo',1,'RGB24_352x288'); %

% Para acceder a la información de este objeto Matlab:

get(video)
%   General Settings:
%       DeviceID = 1
%       DiskLogger = []
%       DiskLoggerFrameCount = 0
%       EventLog = [1x0 struct]
%       FrameGrabInterval = 1
%       FramesAcquired = 0
%       FramesAvailable = 0
%       FramesPerTrigger = 10
%       Logging = off
%       LoggingMode = memory
%       Name = RGB24_352x288-winvideo-1
%       NumberOfBands = 3
%       Previewing = off
%       ROIPosition = [0 0 352 288]
%       Running = off
%       Tag =
%       Timeout = 10
%       Type = videoinput
%       UserData = []
%       VideoFormat = RGB24_352x288
%       VideoResolution = [352 288]
%
%       Color Space Settings:
%           BayerSensorAlignment = grbg
%           ReturnedColorSpace = rgb
%
%       Callback Function Settings:
%           ErrorFcn = @imaqcallback
%           FramesAcquiredFcn = []
%           FramesAcquiredFcnCount = 0
%           StartFcn = []
%           StopFcn = []
%           TimerFcn = []
%           TimerPeriod = 1
%           TriggerFcn = []
%
%       Trigger Settings:
%           InitialTriggerTime = []
%           TriggerCondition = none
%           TriggerFrameDelay = 0
%           TriggerRepeat = 0
%           TriggersExecuted = 0
%           TriggerSource = none
%           TriggerType = immediate
%
%       Acquisition Sources:
%           SelectedSourceName = input1
%           Source = [1x1 videosource]

% Para ver una pequeña descripción de lo que es cada parámetro:
% imaqhelp videoinput

% Todos estos parámeros son modificables abriendo el objeto de video. Doble
% click en el workspace.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Para capturar una imagen independiente (no afecta el número de disparos y
% frames por disparos):

preview(video)
% se abre una pantalla gráfica que muestra lo que visualiza la cámara
I = getsnapshot(video);
% captura la imagen que se está visualizando la cámara
% en el momento de la llamada
% Antes de capturar hay que previsualizar (si no se captura una imagen en
% negro)
imtool(I) % para mostrar la imagen por imtool

% Hay cámaras que no ofrecen modelo RGB de salida, sino que ofrecen modelos
% de color basados en luminancia y dos componentes cromáticas YCbCr,
% YUY,...
% Hay que aplicar alguna función MATLAB que transforme el modelo de color a RGB
% Esta función es: ycbcr2rgb.m para modelos YCbCr.

video=videoinput('winvideo',1,'I420_320x240'); %
preview(video)
I = getsnapshot(video);
image(I)
Imod=ycbcr2rgb(I);
imshow(Imod)

% Otra opción es editar el objeto video y seleccionar el modelo de color de
% salida de la imagen - En ReturnedColorSpace
% De esta forma, se hace la conversión de forma automática, sin necesidad
% de aplicar ninguna función.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% VIDEO: Adquisición de imágenes, frames, continuada. Parámetros de interés

video.TriggerRepeat=3; % set(video,'TriggerRepeat',Inf);
% número de disparos adicionales programados para el dispositivo.
% si tiene un valor 3, se ejecutan 4 disparos

% video.TriggerRepeat=inf; % set(video,'TriggerRepeat',Inf); Con esta
% configuración, infinitos disparos

video.FramesPerTrigger=3;
% Número de imágenes o frames que se capturan por disparo

video.FrameGrabInterval=3;
% Respecto a los frames que la camara puede adquirir a su máxima velocidad
% de captura (típicamente 30 fps), se almacenan en memoria el primero de cada tres hasta
% un total de (Número de disparos * Frames por Disparos).
% Este paramétro es importante porque determina los frames por segundo fps
% a la que se graba en memoria (para que un video se pueda ver de forma
% aceptable se deben mostrar al menos 1 fps).

% Si por ejemplo el dispositivo de video es capaz de capturar 4 fps
% y se fija el FrameGrabInterval a 2,
% las imágenenes se han grabado en memoria con una tasa de 2fps.

% LoggingMode = memory;
% el almacenamiento de los frames es en memoria -también puede ser en disco

% TriggerType = immediate
% El número de disparos programado es inmediato, uno detrás de otro

% La otra opción es disparar de forma manual, si lo permite el dispositivo.

% Video.FramesAcquired
% En esta variable se almacena el número de Frames que se han adquirido
% con getdata. La instrucción getdata permite guardar como variable matlab
% uno o varios frames guardados en memoria.

% Para cambiar estos parámetros, puede hacerse como se ha mostrado o
% haciendo doble click en el objeto video en el Workspace y modificar las
% opciones.

% Para comenzar a capturar una secuencia de frames:

start(video) % el dispositivo de video empieza a funcionar con la
% configuración almacenada en el objeto.

% Si el disparo es inmediato y el número de disparos infinito, está
% continuamente capturando fotos hasta que se llame la función stop(video).
% No todos los frames se guardan en memoria
% - sólo los que indica video.FrameGrabInterval

stop(video) % para detener una adquisición de video


% CREAR UNA SECUENCIA DE 50 FRAMES Y MOSTRARLA:

% 1 OPCIÓN: CAPTURARLOS Y GUARDARLOS EN MEMORIA TODOS, Y DESPUÉS MOSTRARLOS
% ESTA OPCIÓN NO SE APLICA PERO ES ILUSTRATIVA DEL FUNCIONAMIENTO

video.TriggerRepeat=1 % dos disparos programados para el dispositivo.

video.FramesPerTrigger=25;
% Número de imágenes o frames que se capturan por disparo

video.FrameGrabInterval=2;

% Con esta configuración se graban en memoria uno de cada dos frames
% que el dispositivo es capaz de capturar a su máxima velocidad.
% Se reducen los fps guardados en memoria a la mitad.
% El número de frames capturados siguen siendo 50, pero están más
% espaciados en el tiempo.

start(video)

% Cuando termina, se puede ver un reporte con video: se puede comprobar que
% hay 50 frames disponibles con getdata
% Para mostrarlos:
N=((video.TriggerRepeat+1)*video.FramesPerTrigger);
% N es el número de frames guardados en memoria

% Accedemos a la memoria para ir cogiendo frames de uno en uno y los vamos
% mostrando con imshow
figure, hold on
for i=1:N
    I=getdata(video,1);
    imshow(I)
end

start(video)
% Accedemos de golpe a toda la información de la memoria y la mostramos
I=getdata(video,N);
[Filas Columnas Bandas Imagenes]=size(I);

% Primera imagen: I(:,:,1,1)
% Última imagen: I(:,:,1,N)

for i=1:N
    imagen=I(:,:,1,i);
    figure,imshow(imagen) % se abren N imágenes pero podemos ver lo grabado
end

% SEGUNDA OPCIÓN QUE ES LA QUE SE UTILIZA:
% Se programan infinitos disparos y el video termina cuando se han
% adquirido de la memoria un número determinado de frames

video.TriggerRepeat=inf; % disparos continuados

video.FramesPerTrigger=1;
% Número de imágenes o frames que se capturan por disparo

video.FrameGrabInterval=1; % Hacer para un valor 10 y 20;

start(video)

while (video.FramesAcquired<50)

    I=getdata(video,1); % captura un frame guardado en memoria. A medida que se va llamando
    % a esta función se van capturando los frames en el mismo orden cronológico en que fueron guardados
    
    % Ver la ayuda de esta función: admite guardar simultánemente un número mayor de frames, en cuyo
    % caso se almacena en I un vector de frames.
    
    imshow(255-I) % para ir mostrando la secuencia de frames - en este caso se muestra la imagen complemtaria
end

stop(video)

% Para ver el reporte de los frames que se han capturado con getdata
% y los que quedan por capturar guardados en memoria:

video

% SELECCIÓN DE video.FrameGrabInterval
% La función getdata permite guardar información temporal
% de cuando se han tomado los frames. Esto es importante porque, fijando
% video.FrameGrabInterval a 1 (es decir se guardan los frames a la máxima
% velocidad de captura en memoria), permite tener una idea de los fps
% que nuestro dispositivo de video es capaz de capturar.
% En base a ello podemos fijar
% el número de frames por segundo que queremos que
% se graben en memoria a través del parámetro video.FrameGrabInterval, para
% que la secuencia de video registrada se visualice con un mínimo de 1fps.

% Un Ejemplo sería:

video.FrameGrabInterval=1;
start(video)
TIEMPO=[];
while (video.FramesAcquired<150)
    % Como ahora se graban todos los frames a la velocidad de captura de la
    % cámara, varios frames por segundo,
    % para que la secuencia dure un poco más de tiempo hay que programar un
    % mayor número de frames adquiridos con getdata
    [I TIME METADATA]=getdata(video,1);
    TIEMPO=[TIEMPO ; TIME];
    gamma=1.5;
    I=imadjust(I,[],[],gamma);
    %imshow(I) % para ir mostrando la secuencia de frames - en este caso se muestra una secuencia más "clara"
end
stop(video)
video

% En este ejemplo se ha guardado en la variable TIEMPO los instantes de
% tiempo, contados desde el primer disparo, en los que se
% capturan los frames que se graban



% EJEMPLO:
% 1.- Visualizar una secuencia de video que muestre el seguimiento de una determinada zona de la escena. Esta zona será proporcionada al proceso mediante una imagen almacenada en el ordenador. Utilizaremos la correlación normalizada para realizar el seguimiento.

clear all
clc

datos=imaqhwinfo('winvideo');
video=videoinput('winvideo',1,'YUY2_320x240'); %
video.ReturnedColorSpace='grayscale';

% CAPTURAMOS UNA IMAGEN PARA EXTRAER LA PLANTILLA
preview(video) % se abra una pantalla gráfica que muestra lo que visualiza la cámara (1fps)
I = getsnapshot(video);

% De forma manual
imtool(I) % para mostrar la imagen por imtool y sacar las coordenadas de la plantilla
imtool close all
fila1=50; fila2=75; columna1=155; columna2=180;
Plantilla=I(fila1:fila2,columna1:columna2);
imshow(Plantilla)

% De forma automatizada
% Utilizamos la instrucción roipoly para seleccionar un área de interés
% Pinchamos cuatro veces crear el polígono de interés y doble click.

roi = roipoly(I); % Matriz lógica, donde a 1 se marcan
%los píxeles de interés

[filas columnas]=find(roi==1); % Coordenadas de los pixeles que integran
% la region de interés
fila1=min(filas); fila2=max(filas);
columna1=min(columnas); columna2=max(columnas);
Plantilla=I(fila1:fila2,columna1:columna2);
imshow(Plantilla)

[NT MT]=size(Plantilla);


% Para capturar una secuencia de frames:
video.TriggerRepeat=inf;

video.FrameGrabInterval=5; % de todos los frames que se capturan, sólo se van grabando de 5 en 5.

start(video) % el dispositivo de video empieza a funcionar con la configuración almacenada en el objeto.

while (video.FramesAcquired<150)

    I=getdata(video,1); % captura un frame guardado en memoria.
    ncc = normxcorr2(Plantilla,I);
    [Nncc Mncc]=size(ncc);
    ncc=ncc(1+floor(NT/2):Nncc-floor(NT/2),1+floor(MT/2):Mncc-floor(MT/2));
    [i j]=find(ncc==max(ncc(:)));

    imshow(I),hold on, plot(j,i,'R*'),hold off

end

stop(video)
delete(video);
clear video;