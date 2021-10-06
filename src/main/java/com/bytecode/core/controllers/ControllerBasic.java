package com.bytecode.core.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bytecode.core.configuration.Paginas;
import com.bytecode.core.models.Productos;

@Controller
@RequestMapping("/home")
public class ControllerBasic {
	
	public List<Productos> getProductos(){
		ArrayList <Productos> productos = new ArrayList<>();
		productos.add(new Productos(1, "Melocotones" ,1, 25505, 19, 30351));
		productos.add(new Productos(2, "Manzanas", 3, 18108, 19, 21549));
		productos.add(new Productos(3, "Plátanos", 4, 29681, 19, 35320));
		productos.add(new Productos(4, "Lechuga", 3, 29788, 19, 35448));
		productos.add(new Productos(5, "Tomates", 1, 12739, 19, 15159));
		return productos;
	}

	@GetMapping(path = {"/productos","/"})
	public String saludar(Model model)	{
		//"producto" es la variable que reconoce el thymeleaf en el html "index"
		model.addAttribute("producto",this.getProductos());
		return "index";
	}
	
	@GetMapping(path = "/public")
	public ModelAndView productos() {
		ModelAndView modelAndView = new ModelAndView(Paginas.HOME);
		modelAndView.addObject("producto",this.getProductos());
		return modelAndView;
	}

	@GetMapping(path = {"/producto","/producto/{var_producto}"})
	public ModelAndView getProductoIndividual(
			@RequestParam(defaultValue = "1", name = "codigo_producto", required = false)
			// el paTH variable no funciona, manda al valor 1 siempre
			@PathVariable(required = true, name = "var_producto") long codigo_producto
			) {
		ModelAndView modelAndView = new ModelAndView(Paginas.PRODUCTOS);
		List <Productos> productoFiltrado = this.getProductos().stream()
											.filter( (p) -> {
												return p.getCodigo_producto() == codigo_producto;
											}).collect(Collectors.toList());
		modelAndView.addObject("productoXD",productoFiltrado.get(0));
		return modelAndView;
	}

	@GetMapping("/productoNew")
	public ModelAndView getForm() {
		return new ModelAndView("formproducto").addObject("par_producto", new Productos());
	}

	@PostMapping("/addNewProducto")
	public String addNewProducto(Productos par_producto, Model model){
		List<Productos> list_productos = this.getProductos();
		list_productos.add(par_producto);
		//"producto" es la variable que reconoce el thymeleaf en el html "index"
		model.addAttribute("producto", list_productos);
		return "index";
	}
}