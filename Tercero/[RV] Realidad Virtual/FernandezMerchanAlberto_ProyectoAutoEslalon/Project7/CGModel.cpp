#include "CGModel.h"
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <GLFW/glfw3.h>
#include <iostream>
#include "CGCamera.h"
#include "CGScene.h"
#include "CGTriangle.h"

//
// FUNCIÓN: CGModel::initialize(int, int)
//
// PROPÓSITO: Initializa el modelo 3D
//
void CGModel::initialize(int w, int h)
{
    // Crea el programa
    program = new CGShaderProgram();
    if (program->IsLinked() == GL_TRUE) program->Use();
    else std::cout << program->GetLog() << std::endl;

    // Inicializa las sombras

    bool frameBufferStatus = InitShadowMap();
    if (!frameBufferStatus) std::cout << "FrameBuffer incompleto" << std::endl;

    // Crea la escena
    scene = new CGScene();

    coche = scene->getCoche();
    arrayArcos = scene->getArcos();
    etiquetaFinal = scene->getFin();

    view = glm::mat4(0.0f);
   
    Dir = glm::vec3(coche->GetLocation()[1][0], coche->GetLocation()[1][1], -coche->GetLocation()[1][2]);
    Right = glm::cross(Up, Dir);
    Pos = glm::vec3((coche->GetLocation()[3][0] + coche->GetLocation()[1][0]), coche->GetLocation()[3][1] + 4, (coche->GetLocation()[3][2] - 20));
    matrix[0][0] = Right.x;
    matrix[1][0] = Right.y;
    matrix[2][0] = Right.z;
    matrix[3][0] = 0.0f;
    matrix[0][1] = Up.x;
    matrix[1][1] = Up.y;
    matrix[2][1] = Up.z;
    matrix[3][1] = 0.0f;
    matrix[0][2] = Dir.x;
    matrix[1][2] = Dir.y;
    matrix[2][2] = Dir.z;
    matrix[3][2] = 0.0f;
    matrix[0][3] = 0.0f;
    matrix[1][3] = 0.0f;
    matrix[2][3] = 0.0f;
    matrix[3][3] = 1.0f;

    view = glm::translate(matrix, -Pos);
    //matrix = glm::mat4(1.0f);

    mat1 = scene->getMaterialSiguiente();

    // Asigna el viewport y el clipping volume
    resize(w, h);

    // Opciones de dibujo
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glFrontFace(GL_CCW);
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
}

bool CGModel::InitShadowMap()
{
    GLfloat border[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    GLsizei shadowMapWidth = 1024;
    GLsizei shadowMapHeight = 1024;

    glGenFramebuffers(1, &shadowFBO);
    glBindFramebuffer(GL_FRAMEBUFFER, shadowFBO);

    glGenTextures(1, &depthTexId);
    glActiveTexture(GL_TEXTURE2);
    glBindTexture(GL_TEXTURE_2D, depthTexId);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, shadowMapWidth,
        shadowMapHeight, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
    glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, border);
    glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTexId, 0);

    glDrawBuffer(GL_NONE);

    bool result = true;
    if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
    {
        result = false;
    }

    glBindFramebuffer(GL_FRAMEBUFFER, 0);

    return result;
}

//
// FUNCIÓN: CGModel::finalize()
//
// PROPÓSITO: Libera los recursos del modelo 3D
//
void CGModel::finalize()
{
    delete scene;
    delete program;
}

//
// FUNCIÓN: CGModel::resize(int w, int h)
//
// PROPÓSITO: Asigna el viewport y el clipping volume
//
void CGModel::resize(int w, int h)
{
    double fov = glm::radians(15.0);
    double sin_fov = sin(fov);
    double cos_fov = cos(fov);
    if (h == 0) h = 1;
    GLfloat aspectRatio = (GLfloat)w / (GLfloat)h;
    GLfloat wHeight = (GLfloat)(sin_fov * 0.2 / cos_fov);
    GLfloat wWidth = wHeight * aspectRatio;

    wndWidth = w;
    wndHeight = h;

    glViewport(0, 0, w, h);
    projection = glm::frustum(-wWidth, wWidth, -wHeight, wHeight, 0.2f, 400.0f);
}

//
// FUNCIÓN: CGModel::render()
//
// PROPÓSITO: Genera la imagen
//
void CGModel::render()
{
    //*********************************************************//
    //                  Genera el ShadowMap                    //
    //*********************************************************//

    // Asigna las matrices Viewport, View y Projection de la luz.
    glm::mat4 lightViewMatrix = scene->GetLightViewMatrix();
    float dim = 150.0f;
    float w = wndWidth;
    float h = wndHeight;
    glm::mat4 lightPerspective = glm::ortho(-dim, dim, -dim, dim, -400.0f, 400.0f);
    glm::mat4 lightMVP = lightPerspective * lightViewMatrix;

    // Activa el framebuffer de la sombra
    glBindFramebuffer(GL_FRAMEBUFFER, shadowFBO);

    // Limpia la información de profundidad
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Selecciona la subrutina recordDepth
    program->SetFragmentShaderUniformSubroutine("recordDepth");

    // Activa front-face culling
    glCullFace(GL_FRONT);

    //Asigna el viewport
    glViewport(0, 0, 1024, 1024);

    // Dibuja la escena
    scene->Draw(program, lightPerspective, lightViewMatrix, lightMVP);

    //*********************************************************//
    //                  Dibuja la escena                       //
    //*********************************************************//

    // Activa el framebuffer de la imagen
    glBindFramebuffer(GL_FRAMEBUFFER, 0);

    // Limpia el framebuffer
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Activa back-face culling
    glCullFace(GL_BACK);

    // Selecciona la subrutina shadeWithShadow
    program->SetFragmentShaderUniformSubroutine("shadeWithShadow");
    program->SetUniformI("ShadowMap", 2);

    // Asigna el viewport
    glViewport(0, 0, wndWidth, wndHeight);

    // Dibuja la escena
    scene->Draw(program, projection, view, lightMVP);

}


bool CGModel::haPasadoPorElArco() {
    return (coche->GetLocation()[3][0] > (arrayArcos[arcoSiguiente]->GetLocation()[3][0]) - RADIO_INTERNO &&
        coche->GetLocation()[3][0] < (arrayArcos[arcoSiguiente]->GetLocation()[3][0]) + RADIO_INTERNO &&
        (coche->GetLocation()[3][2]) > (arrayArcos[arcoSiguiente]->GetLocation()[3][2]) - (RADIO_EXTERNO - RADIO_INTERNO) &&
        (coche->GetLocation()[3][2]) < (arrayArcos[arcoSiguiente]->GetLocation()[3][2]) + (RADIO_EXTERNO - RADIO_INTERNO));
}


bool CGModel::fin() {
    return arcoSiguiente == NUM_ARCOS_TOTAL;
}

void CGModel::getSize(GLfloat& w, GLfloat& h) {
    w = wWidth;
    h = wHeight;

}

int test = 0;
float giro = 0.0f;

//
// FUNCIÓN: CGModel::update()
//
// PROPÓSITO: Anima la escena
//
void CGModel::update()
{

    if (fin()) { 

        Dir = glm::vec3(etiquetaFinal->GetLocation()[2][0], etiquetaFinal->GetLocation()[2][1], -etiquetaFinal->GetLocation()[2][2]);
        Right = glm::cross(Up, Dir);
        matrix[0][0] = Right.x;
        matrix[1][0] = Right.y;
        matrix[2][0] = Right.z;
        matrix[0][2] = Dir.x;
        matrix[1][2] = Dir.y;
        matrix[2][2] = Dir.z;
        Pos = glm::vec3((etiquetaFinal->GetLocation()[3][0]) + 5, etiquetaFinal->GetLocation()[3][1] + 2, (etiquetaFinal->GetLocation()[3][2]) - 15);
        view = glm::translate(matrix, -Pos);

    }
    else {
        // Detectar si pasa por el arco siguiente.
        if (haPasadoPorElArco()) {

            //Cambia el color del arco que acaba de pasar.
            arrayArcos[arcoSiguiente]->SetMaterial(scene->getMaterialPasado());
            arcoSiguiente++;
            //Cambia el color del arco siguiente.
            if(arcoSiguiente < NUM_ARCOS_TOTAL)
            arrayArcos[arcoSiguiente]->SetMaterial(scene->getMaterialSiguiente());

        }

        // Por cada actualización, el coche avanzará según su velocidad.
        coche->Translate(glm::vec3(0.0f, velocidad, 0.0f));
        CameraConstraints(); // Restricciones de movimiento del mapa.

        if (velocidad > 0.05f || (camaraTrasera == 1 && (velocidad > -0.05f && velocidad < 0.05))) {
            Dir = glm::vec3(-coche->GetLocation()[1][0]  , coche->GetLocation()[1][1], -coche->GetLocation()[1][2]);
            camaraTrasera = 0;
        }
        else if (velocidad < -0.05f && velocidad != 0.0f) {
            Dir = glm::vec3(coche->GetLocation()[1][0], coche->GetLocation()[1][1], coche->GetLocation()[1][2]);
            camaraTrasera = 1;
        }

        Right = glm::cross(Up, Dir);

        matrix[0][0] = Right.x;
        matrix[1][0] = Right.y;
        matrix[2][0] = Right.z;

        matrix[0][2] = Dir.x;
        matrix[1][2] = Dir.y;
        matrix[2][2] = Dir.z;

        Pos = glm::vec3((coche->GetLocation()[3][0] + 20 * (Dir.x)), coche->GetLocation()[3][1] + 4 + test, (coche->GetLocation()[3][2]) + 20 * Dir.z );

        view = glm::translate(matrix, -Pos);
    }
}


//
// FUNCIÓN: CGModel::key_pressed(int)
//
// PROPÓSITO: Respuesta a acciones de teclado
//
void CGModel::key_pressed(int key)
{
    switch (key)
    {
    case GLFW_KEY_UP:
    case GLFW_KEY_W:
        if (velocidad < 1.0f) velocidad += 0.1f;
        break;
    case GLFW_KEY_DOWN:
    case GLFW_KEY_S:
        if (velocidad > -1.0f) velocidad -= 0.1f;
        break;
    case GLFW_KEY_LEFT:
    case GLFW_KEY_A:
        coche->Rotate(velocidad * 5, glm::vec3(0.0f, 0.0f, 1.0f));


        giro += 0.1;
        if (giro > 1) giro = 1;
        if (velocidad > -0.05f && velocidad < 0.05)  coche->Rotate(15.0f, glm::vec3(0.0f, 0.0f, 1.0f));

        break;
    case GLFW_KEY_RIGHT:
    case GLFW_KEY_D:
        giro -= 0.1;
        if (giro < -1) giro = -1;
        coche->Rotate(-velocidad * 5, glm::vec3(0.0f, 0.0f, 1.0f));
        if (velocidad > -0.05f && velocidad < 0.05)  coche->Rotate(-15.0f, glm::vec3(0.0f, 0.0f, 1.0f));
        break;

    case GLFW_KEY_P:
        velocidad = 0.0f;
        break;

    case GLFW_KEY_U:
        test += 1;
        break;

    case GLFW_KEY_J:
        test -= 1;
        break;

    case GLFW_KEY_R:
        
        velocidad = 0.0f;
        arcoSiguiente = 0;
        camaraTrasera = 0;

        scene->resetArcos();
        coche->SetLocation(scene->getLocCocheOriginal());

        Dir = glm::vec3(coche->GetLocation()[1][0], coche->GetLocation()[1][1], -coche->GetLocation()[1][2]);
        Right = glm::cross(Up, Dir);
        Pos = glm::vec3((coche->GetLocation()[3][0] + coche->GetLocation()[1][0]), coche->GetLocation()[3][1] + 4, (coche->GetLocation()[3][2] - 20));
        matrix[0][0] = Right.x;
        matrix[1][0] = Right.y;
        matrix[2][0] = Right.z;
        matrix[3][0] = 0.0f;
        matrix[0][1] = Up.x;
        matrix[1][1] = Up.y;
        matrix[2][1] = Up.z;
        matrix[3][1] = 0.0f;
        matrix[0][2] = Dir.x;
        matrix[1][2] = Dir.y;
        matrix[2][2] = Dir.z;
        matrix[3][2] = 0.0f;
        matrix[0][3] = 0.0f;
        matrix[1][3] = 0.0f;
        matrix[2][3] = 0.0f;
        matrix[3][3] = 1.0f;

        view = glm::translate(matrix, -Pos);


        break;

    }

}

//
//  FUNCIÓN: CGModel:::mouse_button(int button, int action)
//
//  PROPÓSITO: Respuesta del modelo a un click del ratón.
//
void CGModel::mouse_button(int button, int action)
{
    //std::cout << button << "," << action << "\n";
}

//
//  FUNCIÓN: CGModel::mouse_move(double xpos, double ypos)
//
//  PROPÓSITO: Respuesta del modelo a un movimiento del ratón.
//
void CGModel::mouse_move(double xpos, double ypos)
{
    //std::cout << xpos << "," << ypos << "\n";
}

//
//  FUNCIÓN: CGModel::CameraConstraints()
//
//  PROPÓSITO: Limita el movimiento de la cámara a una cierta zona
//
void CGModel::CameraConstraints()
{
    glm::vec3 pos = glm::vec3(coche->GetLocation()[3][0], coche->GetLocation()[3][1], coche->GetLocation()[3][2]);
    int constraint = 0;
    if (pos.y < -1.0f) { pos.y = 0.0f; constraint = 1; }
    if (pos.y > 100.0f) { pos.y = 100.0f; constraint = 1; }
    if (pos.x > 145.0f) { pos.x = 145.0f; constraint = 1; }
    if (pos.x < -145.0f) { pos.x = -145.0f; constraint = 1; }
    if (pos.z > 145.0f) { pos.z = 145.0f; constraint = 1; }
    if (pos.z < -145.0f) { pos.z = -145.0f; constraint = 1; }
    if (constraint == 1)
    {
        coche->SetPosition(pos);
        velocidad = 0.0f;
    }
}