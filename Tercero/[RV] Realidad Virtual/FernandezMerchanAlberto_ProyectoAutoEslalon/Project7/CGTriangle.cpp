#include "CGTriangle.h"
#include <GL/glew.h>

//
// FUNCI�N: CGTriangle::CGTriangle()
//
// PROP�SITO: Crea un tri�ngulo formado por tres v�rtices
//
CGTriangle::CGTriangle()
{
    int numVertices = 3;
    int numFaces = 1;

    GLfloat vertices[9] = {
      -0.01f, -0.01f, 0.5f,
      0.01f, -0.01f, 0.5f,
      0.0f, 0.01f, 0.5f
    };

    // Create the Vertex Array Object
    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);

    // Create the Vertex Buffer Objects
    glGenBuffers(1, &VBO);

    // Vertex data
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(GLfloat) * numVertices * 3, vertices,
        GL_STATIC_DRAW);

    glEnableVertexAttribArray(0); // Vertex position
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
}

//
// FUNCI�N: CGTriangle::~CGTriangle()
//
// PROP�SITO: Destruye el tri�ngulo
//
CGTriangle::~CGTriangle()
{
    // Delete vertex buffer objects
    glDeleteBuffers(1, &VBO);

    // Delete vetex array object
    glDeleteVertexArrays(1, &VAO);
}

//
// FUNCI�N: CGTriangle::Draw(CGShaderProgram*, GLfloat, GLfloat, GLfloat)
//
// PROP�SITO: Dibuja el tri�ngulo
//
void CGTriangle::Draw(CGShaderProgram* program, GLfloat posX, GLfloat posY,
    GLfloat size)
{
    program->SetUniformF("posX", posX);
    program->SetUniformF("posY", posY);
    program->SetUniformF("size", size);

    glBindVertexArray(VAO);
    glDrawArrays(GL_TRIANGLES, 0, 3);
}