% 
% Función Ietiq = etiquetar_imagenes(Ib)
% Dada una imagen binaria (escala de grises), devuelve una imagen
% con cada objeto etiquetado.
% 
% Para detectar si un pixel pertenece a un objeto o a otro se usará
% el principio de vecindad tipo 8. (Se puede cambiar por vecindad tipo 4).
% 
% v8 = Ib(f-1:2:f+1,c-1:2:c+1);
% v4 = [Ib(f-1:2:f+1,c); Ib(f,c-1:2:c+1)']
% 
% 
% 
% 
%
function [Ietiq, valores_unicos] = etiquetar_imagen(Ib)

    [nF,nC] = size(Ib);
    % Inicialización
    %A cada pixel == 1 se le asocia una etiqueta distinta.

    Ietiq = zeros(size(Ib));    % Inicializo la matriz resultado al tamaño
                                % de la matriz de los parámetros
    numOnes = sum(Ib(:));       % Cuento cuantos pixeles a nivel alto 
                                % existen en la imagen binaria.

    Ietiq((Ib == 1)) = 1:numOnes; % Para las posiciones de la imagen resultado 
                                %que coincidan con las posiciones a nivel 
                                %alto de la imagen binaria le asigno un 
                                % número de 1 hasta el número total de unos 
                                % que existen en la matriz binaria

    % Recorrido 1. Pasada de arriba-abajo
    % Para cada pixel etiquetado elegir la etiqueta mínima de sus vecinos
    % != 0 en la misma línea o en la previa.

    %Reescalar la imagen a una con 2 columnas y 2 filas más llenas de 0
    % haciendo un "marco" para poder acceder a los pixeles de los laterales.

    etiquetas = [zeros(1,nC+2);zeros(nF,1),[Ietiq],zeros(nF,1);zeros(1,nC+2)];
    % etiquetas = Ietiq(2:F+1,2:C+1)
    cambio = true;
%Se puede hacer todo sobre la misma matriz.
while(cambio == true)
    cambio = false;
    for f=1:nF         % 2:nF+1        
        for c=1:nC     % 2:nC+1
            if(Ietiq(f,c) ~= 0)
                % Elegir la etiqueta mínima de sus vecinos.
                V8 = unique(vecinos(etiquetas,f+1,c+1));
                V8 = V8(V8 ~= 0);
                minima_etiqueta = min(V8);
                if(minima_etiqueta ~= Ietiq(f,c))
                    cambio = true;
                    Ietiq(f,c) = minima_etiqueta;
                    etiquetas(f+1,c+1) = minima_etiqueta;
                end

            end
        end
    end
    % Recorrido 2. Pasada de abajo-arriba
    for f=nF:-1:1                 
        for c=nC:-1:1
            if(Ietiq(f,c) ~= 0)
                % Elegir la etiqueta mínima de sus vecinos.
                V8 = unique(vecinos2(etiquetas,f+1,c+1));
                V8 = V8(V8 ~= 0);
                minima_etiqueta = min(V8);
                if(minima_etiqueta ~= Ietiq(f,c))
                    cambio = true;
                    Ietiq(f,c) = minima_etiqueta;
                    etiquetas(f+1,c+1) = minima_etiqueta;
                end

            end
        end
    end

end

%Si se hace todo sobre la misma matriz habría que eliminar
%las columnas y filas añadidas artificialmente.
valores_unicos = unique(Ietiq);                     % Saco los valores únicos que la matriz etiquetada
valores_unicos = valores_unicos(valores_unicos >0); % Elimino el 0 de esos valores (no nos sirve).
%Asigna las etiquetas de 1:N
 for i=1:size(valores_unicos,1)
     Ietiq(Ietiq == valores_unicos(i)) = i; % Para cada valor de la etiqueta le reasigno un valor de 1 hasta el número de valores que existan
 end

end

function V8 = vecinos(Ietiq,f,c)
     V8 = [Ietiq(f-1,c-1:c+1),Ietiq(f,c-1:c)];  % Vecindad Tipo 8 (Primera pasada)
     %V4 = [Ietiq(f-1,c-1),Ietiq(f,c-1)]        % vecindad Tipo 4 (Primera pasada)
end

function V8 = vecinos2(Ietiq,f,c)
     V8 = [Ietiq(f+1,c-1:c+1),Ietiq(f,c:c+1)];  % Vecindad Tipo 8 (Segunda pasada)
     %V4 = [Ietiq(f+1,c),Ietiq(f,c+1)]          % Vecindad Tipo 4 (Segunda pasada)
end
