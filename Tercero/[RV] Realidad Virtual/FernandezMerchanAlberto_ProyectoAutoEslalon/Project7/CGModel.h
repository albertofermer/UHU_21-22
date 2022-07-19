#pragma once

#include <GL/glew.h>
#include "CGShaderProgram.h"
#include "CGScene.h"
#include "CGCamera.h"
#include "CGTriangle.h"

class CGModel
{
public:
	void initialize(int w, int h);
	void finalize();
	void render();
	void update();
	void key_pressed(int key);

	bool haPasadoPorElArco();
	bool fin();
	void getSize(GLfloat &w, GLfloat &h);

	void mouse_button(int button, int action);
	void mouse_move(double xpos, double ypos);
	void resize(int w, int h);

private:
	GLsizei wndWidth;
	GLsizei wndHeight;
	GLuint shadowFBO;
	GLuint depthTexId;
	bool InitShadowMap();
	void CameraConstraints();
	CGShaderProgram* program;
	CGScene* scene;
	CGTriangle* triangle;
	glm::mat4 projection;
	glm::mat4 matrix = glm::mat4();
	glm::mat4 view;
	glm::vec3 Dir;
	glm::vec3 Right;
	glm::vec3 Pos;
	glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);
	glm::vec3 direccionInicialCamara;
	GLfloat wHeight;
	GLfloat wWidth;

	CGObject* coche;
	CGFigure* etiquetaFinal;
	CGFigure** arrayArcos;

	float incr = 0.0;
	float velocidad = 0.0;
	int camaraTrasera = 0;

	int arcoSiguiente = 0;


	CGMaterial* mat1;
};
