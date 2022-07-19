#include "CGCamera.h"
#include <GL/glew.h>
#include <math.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>

//
// FUNCIÓN: CGCamera::CGCamera()
//
// PROPÓSITO: Construye una cámara
//
// COMENTARIOS: 
//     La posición inicial es (0,0,0).
//     La orientación incial es el sistema de coordenadas del modelo
//     El tamaño del paso inicial es 0.1
//     El tamaño del giro inicial es 1.0 grados
//
CGCamera::CGCamera()
{
    Pos = glm::vec3(0.0f, 0.0f, 0.0f);
    Dir = glm::vec3(0.0f, 0.0f, 1.0f);
    Up = glm::vec3(0.0f, 1.0f, 0.0f);
    Right = glm::vec3(1.0f, 0.0f, 0.0f);

    moveStep = 0.1f;
    turnStep = 1.0f;
    cosAngle = (GLfloat)cos(glm::radians(turnStep));
    sinAngle = (GLfloat)sin(glm::radians(turnStep));
}

//
// FUNCIÓN: CGCamera::ViewMatrix()
//
// PROPÓSITO: Obtiene la matriz View para situar la cámara.
//
glm::mat4 CGCamera::ViewMatrix()
{
    // Creates a rotation matrix using vectors Dir, Up and Right
    glm::mat4 matrix(1.0f);
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

    return glm::translate(matrix, -Pos);
}

//
// FUNCIÓN: CGCamera::SetPosition(GLfloat x, GLfloat y, GLfloat z)
//
// PROPÓSITO: Asigna la posición de la cámara con respecto al 
//            sistema de coordenadas del modelo.
//
void CGCamera::SetPosition(GLfloat x, GLfloat y, GLfloat z)
{
    Pos = glm::vec3(x, y, z);
}

//
// FUNCIÓN: CGCamera::SetDirection(GLfloat xDir, GLfloat yDir, GLfloat zDir, 
//                                 GLfloat xUp, GLfloat yUp, GLfloat zUp)
//
// PROPÓSITO: Asigna la orientación de la cámara.
//
void CGCamera::SetDirection(GLfloat xD, GLfloat yD, GLfloat zD, GLfloat xU, GLfloat yU, GLfloat zU)
{
    Dir = glm::vec3(xD, yD, zD);
    Up = glm::vec3(xU, yU, zU);
    Right = glm::cross(Up, Dir);
}

//
// FUNCIÓN: CGCamera::SetMoveStep(GLfloat step)
//
// PROPÓSITO: Asigna el avance en cada paso.
//
void CGCamera::SetMoveStep(GLfloat step)
{
    moveStep = step;
}

//
// FUNCIÓN: CGCamera::SetTurnStep(GLfloat step)
//
// PROPÓSITO: Asigna el ángulo de giro en cada paso.
//
void CGCamera::SetTurnStep(GLfloat step)
{
    turnStep = step;
    cosAngle = (GLfloat)cos(glm::radians(turnStep));
    sinAngle = (GLfloat)sin(glm::radians(turnStep));
}

//
// FUNCIÓN: CGCamera::GetPosition()
//
// PROPÓSITO: Obtiene la posición de la cámara.
//
glm::vec3 CGCamera::GetPosition()
{
    return Pos;
}

//
// FUNCIÓN: CGCamera::GetDirection()
//
// PROPÓSITO: Obtiene la orientación de la cámara (eje Z).
//
glm::vec3 CGCamera::GetDirection()
{
    return Dir;
}

//
// FUNCIÓN: CGCamera::GetUpDirection()
//
// PROPÓSITO: Obtiene la orientación cenital de la cámara (eje Y).
//
glm::vec3 CGCamera::GetUpDirection()
{
    return Up;
}

//
// FUNCIÓN: CGCamera::GetMoveStep()
//
// PROPÓSITO: Obtiene el avance en cada paso.
//
GLfloat CGCamera::GetMoveStep()
{
    return moveStep;
}

//
// FUNCIÓN: CGCamera::GetTurnStep()
//
// PROPÓSITO: Obtiene el ángulo de giro en cada paso.
//
GLfloat CGCamera::GetTurnStep()
{
    return turnStep;
}

//
// FUNCIÓN: CGCamera::MoveFront()
//
// PROPÓSITO: Mueve el observador un paso (moveStep) en la dirección -Dir 
//
//
void CGCamera::MoveFront()
{
    Pos -= moveStep * Dir;
}

//
// FUNCIÓN: CGCamera::MoveBack()
//
// PROPÓSITO: Mueve el observador un paso (moveStep) hacia atrás en la dirección Dir 
//
//
void CGCamera::MoveBack()
{
    Pos += moveStep * Dir;
}

//
// FUNCIÓN: CGCamera::MoveLeft()
//
// PROPÓSITO: Mueve el observador un paso (moveStep) hacia la izquierda. 
//
//
void CGCamera::MoveLeft()
{
    Pos -= moveStep * Right;
}

//
// FUNCIÓN: CGCamera::MoveRight()
//
// PROPÓSITO: Mueve el observador un paso (moveStep) hacia la derecha. 
//
//
void CGCamera::MoveRight()
{
    Pos += moveStep * Right;
}

//
// FUNCIÓN: CGCamera::MoveUp()
//
// PROPÓSITO: Mueve el observador un paso (moveStep) hacia arriba. 
//
//
void CGCamera::MoveUp()
{
    Pos += moveStep * Up;
}

//
// FUNCIÓN: CGCamera::MoveDown()
//
// PROPÓSITO: Mueve el observador un paso (moveStep) hacia abajo. 
//
void CGCamera::MoveDown()
{
    Pos -= moveStep * Up;
}

//
// FUNCIÓN: CGCamera::TurnRight()
//
// PROPÓSITO: Rota el observador un paso (angleStep) hacia su derecha.
//
void CGCamera::TurnRight()
{
    Dir.x = cosAngle * Dir.x - sinAngle * Right.x;
    Dir.y = cosAngle * Dir.y - sinAngle * Right.y;
    Dir.z = cosAngle * Dir.z - sinAngle * Right.z;

    // Right = Up x Dir
    Right = glm::cross(Up, Dir);
}

//
// FUNCIÓN: CGCamera::TurnLeft()
//
// PROPÓSITO: Rota el observador un paso (angleStep) hacia su izquierda.
//
void CGCamera::TurnLeft()
{
    Dir.x = cosAngle * Dir.x + sinAngle * Right.x;
    Dir.y = cosAngle * Dir.y + sinAngle * Right.y;
    Dir.z = cosAngle * Dir.z + sinAngle * Right.z;

    // Right = Up x Dir
    Right = glm::cross(Up, Dir);
}

//
// FUNCIÓN: CGCamera::TurnUp()
//
// PROPÓSITO: Rota el observador un paso (angleStep) hacia arriba.
//
void CGCamera::TurnUp()
{
    Dir.x = cosAngle * Dir.x - sinAngle * Up.x;
    Dir.y = cosAngle * Dir.y - sinAngle * Up.y;
    Dir.z = cosAngle * Dir.z - sinAngle * Up.z;

    // Up = Dir x Right
    Up = glm::cross(Dir, Right);
}

//
// FUNCIÓN: CGCamera::TurnDown()
//
// PROPÓSITO: Rota el observador un paso (angleStep) hacia abajo.
//
void CGCamera::TurnDown()
{
    Dir.x = cosAngle * Dir.x + sinAngle * Up.x;
    Dir.y = cosAngle * Dir.y + sinAngle * Up.y;
    Dir.z = cosAngle * Dir.z + sinAngle * Up.z;

    // Up = Dir x Right
    Up = glm::cross(Dir, Right);
}

//
// FUNCIÓN: CGCamera::TurnCW()
//
// PROPÓSITO: Rota el observador un paso (angleStep) en sentido del reloj.
//
void CGCamera::TurnCW()
{
    Up.x = cosAngle * Up.x + sinAngle * Right.x;
    Up.y = cosAngle * Up.y + sinAngle * Right.y;
    Up.z = cosAngle * Up.z + sinAngle * Right.z;

    // Right = Up x Dir
    Right = glm::cross(Up, Dir);
}

//
// FUNCIÓN: CGCamera::TurnDown()
//
// PROPÓSITO: Rota el observador un paso (angleStep) en sentido contrario al reloj.
//
void CGCamera::TurnCCW()
{
    Up.x = cosAngle * Up.x - sinAngle * Right.x;
    Up.y = cosAngle * Up.y - sinAngle * Right.y;
    Up.z = cosAngle * Up.z - sinAngle * Right.z;

    // Right = Up x Dir
    Right = glm::cross(Up, Dir);
}
