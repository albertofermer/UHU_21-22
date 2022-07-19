#include "CGScene.h"
#include <GL/glew.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include "CGShaderProgram.h"
#include "CGFigure.h"
#include "CGLight.h"
#include "CGMaterial.h"
#include "CGFog.h"
#include "CGSkybox.h"
#include "CGCube.h"
#include "CGDisk.h"
#include "CGTorus.h"
#include "CGArco.h"
#include "CGGround.h"
#include "CGFin.h"
#include "CGSlotCar.h"
#include "CGCircuit.h"
#include <iostream>

//
// FUNCIÓN: CGScene::CGScene()
//
// PROPÓSITO: Construye el objeto que representa la escena
//
CGScene::CGScene()
{
    skybox = new CGSkybox();

    fog = new CGFog();
    fog->SetMaxDistance(500.0f);
    fog->SetMinDistance(50.0f);
    fog->SetFogColor(glm::vec3(0.8f, 0.8f, 0.8f));

    glm::vec3 Ldir = glm::vec3(1.0f, -0.8f, -1.0f);
    Ldir = glm::normalize(Ldir);
    light = new CGLight();
    light->SetLightDirection(Ldir);
    light->SetAmbientLight(glm::vec3(0.2f, 0.2f, 0.2f));
    light->SetDifusseLight(glm::vec3(0.8f, 0.8f, 0.8f));
    light->SetSpecularLight(glm::vec3(1.0f, 1.0f, 1.0f));

    matg = new CGMaterial();
    matg->SetAmbientReflect(0.0f, 0.6f, 0.0f);
    matg->SetDifusseReflect(0.0f, 0.6f, 0.0f);
    matg->SetSpecularReflect(0.0f, 0.0f, 0.0f);
    matg->SetShininess(16.0f);
    matg->InitTexture("textures/stone.jpg");

    GLuint textureId = matg->GetTexture();

    ground = new CGGround(150.0f, 150.0f);
    ground->SetMaterial(matg);

    matb = new CGMaterial();
    matb->SetAmbientReflect(0.6f, 0.6f, 0.6f);
    matb->SetDifusseReflect(0.0, 0.0f, 0.0f);
    matb->SetSpecularReflect(0.0f, 0.0f, 0.0f);
    matb->SetShininess(16.0f);
    matb->SetTexture(textureId);

    base = new CGGround(5000.0f, 5000.0f);
    base->SetMaterial(matb);
    base->Translate(glm::vec3(0.0f, -0.05f, 0.0f));

    //Azul
    matArcoSiguiente = new CGMaterial();
    matArcoSiguiente->SetAmbientReflect(0.0f, 0.0f, 1.0f);
    matArcoSiguiente->SetDifusseReflect(0.0f, 0.0f, 1.0f);
    matArcoSiguiente->SetSpecularReflect(0.8f, 0.8f, 0.8f);
    matArcoSiguiente->SetShininess(16.0f);
    matArcoSiguiente->InitTexture("textures/arco.jpg");

    //Gris
    matArcoNoPasado = new CGMaterial();
    matArcoNoPasado->SetAmbientReflect(0.4f, 0.4f, 0.4f);
    matArcoNoPasado->SetDifusseReflect(0.4f, 0.4f, 0.4f);
    matArcoNoPasado->SetSpecularReflect(0.8f, 0.8f, 0.8f);
    matArcoNoPasado->SetShininess(16.0f);
    matArcoNoPasado->InitTexture("textures/arco.jpg");

    //Rojo
    matArcoPasado = new CGMaterial();
    matArcoPasado->SetAmbientReflect(1.0f, 0.0f, 0.0f);
    matArcoPasado->SetDifusseReflect(1.0f, 0.0f, 0.0f);
    matArcoPasado->SetSpecularReflect(0.8f, 0.8f, 0.8f);
    matArcoPasado->SetShininess(16.0f);
    matArcoPasado->InitTexture("textures/arco.jpg");


    // Creación de Arcos

    for (int i = 0; i < NUM_ARCOS_TOTAL; i++) {

        arrayArcos[i] = new CGArco(RADIO_INTERNO, RADIO_EXTERNO, ALTURA_ARCO);
    }

    // Colocamos el primer arco con el material de arco siguiente (azul).
        arrayArcos[0]->SetMaterial(matArcoSiguiente);



    // El resto de arcos los ponemos con el material de arco no pasado (gris).
    for (int i = 1; i < NUM_ARCOS_TOTAL; i++) {
        arrayArcos[i]->SetMaterial(matArcoNoPasado);
    }
   
    // Creación de circuito

    circuito = new CGCircuit(NUM_ARCOS_TOTAL,arrayArcos,RADIO_CIRCUITO);

    
    matFin = new CGMaterial();
    matFin->SetAmbientReflect(1.0f, 1.0f, 1.0f);
    matFin->SetDifusseReflect(1.0f, 1.0f, 1.0f);
    matFin->SetSpecularReflect(0.0f, 0.2f, 0.0f);
    matFin->SetShininess(16.0f);
    matFin->InitTexture("textures/finish.png");

    //GLuint textureId = matFin->GetTexture();
    // Creación Etiqueta Fin
    fin = new CGFin(10.0f, 5.0f);
    fin->SetMaterial(matFin);
    //fin->Translate(glm::vec3(-25.0f, 25.0f, -25.0f));
    fin->Translate(glm::vec3(-25.0f, -55.0f, -25.0f));
    //fin->Rotate(90.0f, glm::vec3(0.0f, 0.0f, 1.0f));
    coche = new CGSlotCar();
    
    coche->Translate(glm::vec3(arrayArcos[0]->GetLocation()[3][0], 0.0f, arrayArcos[0]->GetLocation()[3][1]-2.0f));
    coche->Rotate(-90.0f, glm::vec3(1.0f, 0.0f, 0.0f));
    coche->Rotate(180.0f, glm::vec3(0.0f, 0.0f, 1.0f));

    cocheLoc = coche->GetLocation();

}

//
// FUNCIÓN CGScene::getMaterialNoPasado()
// 
// PROPÓSITO: Obtiene el objeto del material del arco no pasado (gris).
//

glm::mat4 CGScene::getLocCocheOriginal() {

    return cocheLoc;
}

//
// FUNCIÓN CGScene::resetArcos()
// 
// PROPÓSITO: Coloca el material de color gris en todos los arcos y el color azul
//            en el primero.
//

void CGScene::resetArcos() {

    arrayArcos[0]->SetMaterial(matArcoSiguiente);
    for (int i = 1; i < NUM_ARCOS_TOTAL; i++) {
        arrayArcos[i]->SetMaterial(matArcoNoPasado);
    }
}

//
// FUNCIÓN CGScene::getMaterialSiguiente()
// 
// PROPÓSITO: Obtiene el objeto del material del arco siguiente (azul).
//

CGMaterial* CGScene::getMaterialSiguiente() {
    return matArcoSiguiente;
}


//
// FUNCIÓN CGScene::getMaterialPasado()
// 
// PROPÓSITO: Obtiene el objeto del material del arco pasado (rojo).
//

CGMaterial* CGScene::getMaterialPasado() {
    return matArcoPasado;
}

//
// FUNCIÓN CGScene::getMaterialNoPasado()
// 
// PROPÓSITO: Obtiene el objeto del material del arco no pasado (gris).
//

CGMaterial* CGScene::getMaterialNoPasado() {
    return matArcoNoPasado;
}

//
// FUNCIÓN CGScene::getCoche()
// 
// PROPÓSITO: Obtiene el objeto del coche.
//

CGObject* CGScene::getCoche() {
    return coche;

}

//
// FUNCIÓN CGScene::getArcos()
// 
// PROPÓSITO: Obtiene el objeto del array de arcos.
//

CGFigure** CGScene::getArcos() {
    return arrayArcos;

}

// 
// FUNCIÓN: CGscene::getFin()
// 
// PROPÓSITO: Obtiene el objeto de la etiqueta Final
//

CGFigure* CGScene::getFin() {
    return fin;
}
//
// FUNCIÓN: CGScene::~CGScene()
//
// PROPÓSITO: Destruye los objetos que representan la escena
//
CGScene::~CGScene()
{
    delete fin;
    delete base;
    delete ground;
    delete coche;
    delete matb;
    for (int i = 0; i < NUM_ARCOS_TOTAL; i++)
    {
        delete  arrayArcos[i];
    }
    delete[] arrayArcos;
    delete matArcoSiguiente;
    delete matArcoPasado;
    delete matArcoNoPasado;
    delete matFin;
    delete circuito;
    delete light;
    delete fog;
    delete skybox;

}

//
// FUNCIÓN: CGScene::Draw()
//
// PROPÓSITO: Dibuja la escena
//
void CGScene::Draw(CGShaderProgram* program, glm::mat4 proj, glm::mat4 view, glm::mat4 shadowViewMatrix)
{
    light->SetUniforms(program);
    fog->SetUniforms(program);
    skybox->Draw(program, proj, view);
    base->Draw(program, proj, view, shadowViewMatrix);
    ground->Draw(program, proj, view, shadowViewMatrix);
    coche->Draw(program, proj, view, shadowViewMatrix);
    fin->Draw(program, proj, view, shadowViewMatrix);
    for (int i = 0; i < NUM_ARCOS_TOTAL; i++) {
    
        arrayArcos[i]->Draw(program, proj, view, shadowViewMatrix);
    }
    
}

glm::mat4 CGScene::GetLightViewMatrix()
{

    glm::vec3 Zdir = -(light->GetLightDirection());
    glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);
    glm::vec3 Xdir = glm::normalize(glm::cross(Up, Zdir));
    glm::vec3 Ydir = glm::cross(Zdir, Xdir);
    glm::vec3 Zpos = 150.0f * Zdir;
    glm::vec3 Center = glm::vec3(0.0f, 0.0f, 0.0f);

    glm::mat4 view = glm::lookAt(Zpos, Center, Ydir);
    return view;
}
