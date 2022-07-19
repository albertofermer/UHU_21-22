#include "CGFin.h"
#include <GL/glew.h>
#include "CGFigure.h"

CGFin::CGFin(float ancho, float alto)
{
    {
        CGMaterial* mat = new CGMaterial();
        mat->SetAmbientReflect(0.0f, 0.0f, 1.0f);
        mat->SetDifusseReflect(0.0f, 0.0f, 1.0f);
        mat->SetSpecularReflect(0.8f, 0.8f, 0.8f);
        mat->SetShininess(16.0f);

        this->SetMaterial(mat);
        numFaces = 2; // Number of faces
        numVertices = 4; // Number of vertices
        float s = 5.0;
        GLfloat p_vertices[4][3] = {
          { ancho, 0.0f, 0.0f }, // A0 // Positive X
          { ancho, alto, 0.0f }, // D0 
          { 0.0f, alto, 0.0f}, // D1 
          { 0.0f, 0.0f, 0.0f}, // A1 
        };

        GLfloat p_normals[4][3] = {
          { 1.0f, 0.0f, 0.0f }, // Positive X // 0
          { 1.0f, 0.0f, 0.0f }, // Positive X // 1
          { 1.0f, 0.0f, 0.0f }, // Positive X // 2
          { 1.0f, 0.0f, 0.0f }, // Positive X // 3
        };


        GLfloat p_textures[4][2] = {
          { 0.0f, 0.0f },
          { 0.0f, 1.0f },
          { 1.0f, 1.0f },
          { 1.0f, 0.0f }
        };

        GLushort p_indexes[2][3] = { // Array of indexes
          { 0, 2, 1 },
          { 0, 3, 2 }
        };

        vertices = new GLfloat[numVertices * 3];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < 3; j++)
                vertices[3 * i + j] = p_vertices[i][j];
        normals = new GLfloat[numVertices * 3];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < 3; j++)
                normals[3 * i + j] = p_normals[i][j];
        textures = new GLfloat[numVertices * 2];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < 2; j++) textures[2 * i + j] = p_textures[i][j];
        indexes = new GLushort[numFaces * 3];
        for (int i = 0; i < numFaces; i++)
            for (int j = 0; j < 3; j++)
                indexes[3 * i + j] = p_indexes[i][j];

        InitBuffers();
    }
}