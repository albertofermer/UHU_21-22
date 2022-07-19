#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGShaderProgram.h"
#include "CGLight.h"
#include "CGMaterial.h"
#include "CGFigure.h"
#include "CGFog.h"
#include "CGSkybox.h"
#include "CGObject.h"
#include "CGCircuit.h"

#define NUM_ARCOS_TOTAL    10
#define RADIO_INTERNO      3.0f
#define RADIO_EXTERNO      5.0f
#define ALTURA_ARCO        3.0f
#define RADIO_CIRCUITO     50

class CGScene {
public:
    CGScene();
    void Draw(CGShaderProgram* program, glm::mat4 proj, glm::mat4 view, glm::mat4 shadowViewMatrix);
    void resetArcos();
    
    glm::mat4 GetLightViewMatrix();

    CGMaterial* getMaterialSiguiente();
    CGMaterial* getMaterialPasado();
    CGMaterial* getMaterialNoPasado();
    glm::mat4 getLocCocheOriginal();
    CGObject* getCoche();
    CGFigure** getArcos();
    CGFigure* getFin();
    ~CGScene();
private:
    CGCircuit* circuito;
    CGFigure* base;
    CGFigure* ground;
    CGFigure* fin;
    CGFog* fog;
    CGMaterial* matb;
    CGMaterial* matg;
    CGMaterial* matFin;
    CGMaterial* matArcoSiguiente;
    CGMaterial* matArcoPasado;
    CGMaterial* matArcoNoPasado;
    glm::mat4 cocheLoc;
    CGLight* light;
    CGSkybox* skybox;
    CGObject* coche;
    CGFigure* arrayArcos[NUM_ARCOS_TOTAL];
};
