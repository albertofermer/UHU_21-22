%% FUNCIÓN FILTRA_OBJETOS 
% ==========================================
% ENTRADA:
%   - Ib: Matriz binaria.
%   - numPix: Valor que representa el área mínima que se representará.
% 
% SALIDA:
%   - IbFilt: Matriz binaria de las mismas dimensiones que Ib.
%               1: Agrupaciones de Ib cuyo área >= NumPix. 

function IbFilt = filtra_objetos(Ib,numPix)
    IbFilt = zeros(size(Ib));                                               %   La matriz IbFilt tendrá el mismo tamaño que Ib.
    [Ietiq,N] = etiquetar_imagen(Ib);                                       %   Etiquetamos la imagen binaria para obtener los objetos.
    areas = calcula_areas(Ietiq,N);                                         %   De cada objeto calculamos su área.
    [sortedAreas,pos] = sort(areas);                                        %   Ordenamos las áreas de los objetos.
    representar_etiquetas = pos(sortedAreas>=numPix);                       %   Guardamos las etiquetas de los objetos con áreas mayores o iguales a numPix.
    
    for i=1:size(representar_etiquetas,2)                                   %   Para cada una de esas etiquetas
        IbFilt = IbFilt + (Ietiq == representar_etiquetas(1,i));            %   Añadimos el objeto con etiqueta i a la matriz filtrada.
    end
end