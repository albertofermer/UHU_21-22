
#pragma once

#include <GL/glew.h>
#include <glm/glm.hpp>
#include "CGMaterial.h"
#include "CGShaderProgram.h"

#define VERTEX_DATA 0
#define INDEX_DATA 1
#define NORMAL_DATA 2
#define TEXTURE_DATA 3

//
// CLASE: CGPiece
//
// DESCRIPCIÓN: Clase abstracta que representa una pieza descrita mediante
//              VAO para su renderizado mediante shaders
// 
class CGPiece {
protected:
    GLushort* indexes; // Array of indexes 
    GLfloat* vertices; // Array of vertices
    GLfloat* normals; // Array of normals
    GLfloat* textures; // Array of texture coordinates

    GLuint numFaces; // Number of faces
    GLuint numVertices; // Number of vertices
    GLuint VBO[4];
    GLuint VAO;

    glm::mat4 location; // Model matrix respecto del objeto.
    CGMaterial* material;

public:
    ~CGPiece();
    void InitBuffers();
    void SetMaterial(CGMaterial* m);
    void SetLocation(glm::mat4 loc);
    glm::mat4 GetLocation();
    void Translate(glm::vec3 t);
    void Rotate(GLfloat angle, glm::vec3 axis);
    void Draw(CGShaderProgram* program, glm::mat4 projection,
        glm::mat4 view, glm::mat4 model, glm::mat4 shadowViewMatrix);
};