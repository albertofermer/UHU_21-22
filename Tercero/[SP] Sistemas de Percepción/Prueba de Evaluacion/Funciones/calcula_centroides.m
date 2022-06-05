%%      FUNCIÓN CALCULA_CENTROIDES
%======================================================
%   ENTRADA:
%       - Ietiq: Imagen etiquetada de entrada.
%       - N:     Número de objetos etiquetados en Ietiq.
%
%   SALIDA:
%       - centroides: Matriz (Nx2) que representa el centroide de cada uno
%       de los objetos que existen en Ietiq.
% 
% 

function centroides = calcula_centroides(Ietiq,N)                           %   Se determina promediando las coordenadas  x e y de los píxeles que conforman el contorno.
centroides = zeros(size(N,1),2);                                            %   Inicializo la matriz donde se guardarán las coordenadas (x,y) de cada objeto a 0.
    for i=1:size(N,1)                                                       %   Desde 1 hasta el número de objetos que haya en la imagen
        [f,c] = find(Ietiq == i);                                           %   Encuentro las posiciones de 
        x_media = mean(c);
        y_media = mean(f);
        centroides(i,:) = [x_media,y_media];
    end

end