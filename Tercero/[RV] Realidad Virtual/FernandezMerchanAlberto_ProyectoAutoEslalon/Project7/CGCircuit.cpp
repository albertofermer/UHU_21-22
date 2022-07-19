#include "CGCircuit.h"
#include <glm/glm.hpp>

//
// FUNCIÓN: CGCircuit::CGCircuit(int NUM_ARCOS, CGFigure* arcos[], int radio)
// 
// PROPÓSITO: Genera el circuito del juego formando un círculo con los arcos.
//
CGCircuit::CGCircuit(int NUM_ARCOS, CGFigure* arcos[], int radio) {

    for (int i = 0; i < NUM_ARCOS; i++) {
        float alpha = (360.0f / NUM_ARCOS) * i;
        float alpha_rad = glm::radians(alpha);
        arcos[i]->Translate(glm::vec3(radio*cos(alpha_rad), 0.0f, radio*sin(alpha_rad)));
        arcos[i]->Rotate(-alpha,glm::vec3(0.0f,1.0f,0.0f));
    }
}



