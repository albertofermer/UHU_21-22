%%  FUNCIÓN CALCULA_AREAS
%  ============================ 
%  ENTRADA:
%   - Ietiq: Imagen etiquetada de entrada.
%   - N:     Vector con todas las etiquetas que hay en la imagen etiquetada.   
%
%  SALIDA:
%   - areas: Vector (1xN) con el área de cada objeto etiquetado en la
%   imagen Ietiq.
% 
function areas = calcula_areas(Ietiq,N)
    areas = zeros(1,size(N,1));                                             %   Inicializamos el vector fila de áreas a cero.
    
        
     %objeto = [Ietiq == 1:size(N,1)];

    for i=1:size(N,1)                                                       %   Para cada etiqueta,
        objeto = Ietiq == i;                                                %   Generamos la imagen del objeto con etiqueta i
        areas(i) = sum(objeto(:));                                          %   y añadimos a la posición i, su área (la suma de los píxeles a 1).
    end
end