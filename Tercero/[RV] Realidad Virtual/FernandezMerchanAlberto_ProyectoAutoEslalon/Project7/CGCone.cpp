#include "CGCone.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"

///
/// FUNCION: CGCone::CGCone(GLint p, GLint m, GLfloat h, GLfloat r)
///
/// PROPÓSITO: Construir un cono de radio 'r' y altura '2*h'
/// considerando las circunferencias formadas por 'm' puntos
/// y el cuerpo del cono dividido en 'p' rodajas.
///
CGCone::CGCone(GLint p, GLint m, GLfloat h, GLfloat r)
{
    numFaces = 2 * p * m; // Number of faces
    numVertices = (p + 1) * m + 2; // Number of vertices
    vertices = new GLfloat[numVertices * 3];
    normals = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];
    textures = new GLfloat[numVertices * 2];

    double module = sqrt(4 * h * h + r * r);
    double xyN = (GLfloat)(2 * h / module);
    double zN = (GLfloat)(r / module);

    int verticesIndex = 0;
    int normalsIndex = 0;
    int texturesIndex = 0;

    // Centro de la base
    vertices[0] = 0.0f;
    vertices[1] = 0.0f;
    vertices[2] = -h;
    verticesIndex += 3;
    normals[0] = 0.0f;
    normals[1] = 0.0f;
    normals[2] = -1.0f;
    normalsIndex += 3;
    textures[0] = 0.5f;
    textures[1] = 0.5f;
    texturesIndex += 2;

    // Vértices de la base
    for (int i = 0; i < m; i++)
    {
        GLfloat x = (GLfloat)cos(glm::radians(360.0 * i / m));
        GLfloat y = -(GLfloat)sin(glm::radians(360.0 * i / m));
        vertices[verticesIndex] = x * r;
        vertices[verticesIndex + 1] = y * r;
        vertices[verticesIndex + 2] = -h;
        verticesIndex += 3;
        normals[normalsIndex] = 0.0f;
        normals[normalsIndex + 1] = 0.0f;
        normals[normalsIndex + 2] = -1.0f;
        normalsIndex += 3;
        textures[texturesIndex] = x;
        textures[texturesIndex + 1] = y;
        texturesIndex += 2;
    }

    // Extremo del cono
    vertices[verticesIndex] = 0.0f;
    vertices[verticesIndex + 1] = 0.0f;
    vertices[verticesIndex + 2] = h;
    verticesIndex += 3;
    normals[normalsIndex] = 0.0f;
    normals[normalsIndex + 1] = 0.0f;
    normals[normalsIndex + 2] = 1.0f;
    normalsIndex += 3;
    textures[texturesIndex] = 0.5f;
    textures[texturesIndex + 1] = 0.5f;
    texturesIndex += 2;

    // Vértices de los lados
    for (int i = 1; i <= p; i++)
    {
        GLfloat xy = i * 1.0 / p;
        GLfloat z = h - 2 * i * h / p;
        for (int j = 0; j < m; j++)
        {
            GLfloat xN = (GLfloat)cos(glm::radians(360.0 * j / m));
            GLfloat yN = (GLfloat)sin(glm::radians(360.0 * j / m));
            GLfloat x = (GLfloat)(xN * xy);
            GLfloat y = (GLfloat)(yN * xy);
            vertices[verticesIndex] = x * r;
            vertices[verticesIndex + 1] = y * r;
            vertices[verticesIndex + 2] = z;
            verticesIndex += 3;
            normals[normalsIndex] = xN * xyN;
            normals[normalsIndex + 1] = yN * xyN;
            normals[normalsIndex + 2] = zN;
            normalsIndex += 3;
            textures[texturesIndex] = x;
            textures[texturesIndex + 1] = y;
            texturesIndex += 2;
        }
    }

    int indicesIndex = 0;
    // Base
    for (int i = 0; i < m - 1; i++)
    {
        indexes[indicesIndex] = 0;
        indexes[indicesIndex + 1] = i + 1;
        indexes[indicesIndex + 2] = i + 2;
        indicesIndex += 3;
    }

    indexes[indicesIndex] = 0;
    indexes[indicesIndex + 1] = m;
    indexes[indicesIndex + 2] = 1;
    indicesIndex += 3;

    // Extremo
    for (int i = 0; i < m - 1; i++)
    {
        indexes[indicesIndex] = m + 1;
        indexes[indicesIndex + 1] = m + 2 + i;
        indexes[indicesIndex + 2] = m + 3 + i;
        indicesIndex += 3;
    }
    indexes[indicesIndex] = m + 1;
    indexes[indicesIndex + 1] = 2 * m + 1;
    indexes[indicesIndex + 2] = m + 2;
    indicesIndex += 3;

    // Lados
    for (int j = 1; j < p; j++)
    {
        for (int i = 0; i < m - 1; i++)
        {
            indexes[indicesIndex] = j * m + 2 + i;
            indexes[indicesIndex + 1] = (j + 1) * m + 2 + i;
            indexes[indicesIndex + 2] = (j + 1) * m + 3 + i;
            indicesIndex += 3;

            indexes[indicesIndex] = j * m + 2 + i;
            indexes[indicesIndex + 1] = (j + 1) * m + 3 + i;
            indexes[indicesIndex + 2] = j * m + 3 + i;
            indicesIndex += 3;
        }

        indexes[indicesIndex] = (j + 1) * m + 1;
        indexes[indicesIndex + 1] = (j + 2) * m + 1;
        indexes[indicesIndex + 2] = (j + 1) * m + 2;
        indicesIndex += 3;

        indexes[indicesIndex] = (j + 1) * m + 1;
        indexes[indicesIndex + 1] = (j + 1) * m + 2;
        indexes[indicesIndex + 2] = j * m + 2;
        indicesIndex += 3;
    }

    InitBuffers();
}
