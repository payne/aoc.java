package org.mattpayne.aoc.java.puzzle;

import jakarta.validation.Valid;
import org.mattpayne.aoc.java.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    public PuzzleController(final PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("puzzles", puzzleService.findAll());
        return "puzzle/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("puzzle") final PuzzleDTO puzzleDTO) {
        return "puzzle/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("puzzle") @Valid final PuzzleDTO puzzleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "puzzle/add";
        }
        puzzleService.create(puzzleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("puzzle.create.success"));
        return "redirect:/puzzles";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("puzzle", puzzleService.get(id));
        return "puzzle/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("puzzle") @Valid final PuzzleDTO puzzleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "puzzle/edit";
        }
        puzzleService.update(id, puzzleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("puzzle.update.success"));
        return "redirect:/puzzles";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        puzzleService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("puzzle.delete.success"));
        return "redirect:/puzzles";
    }

}
