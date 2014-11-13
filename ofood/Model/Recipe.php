<?php
App::uses('AppModel', 'Model');
/**
 * Recipe Model
 *
 * @property CombinationItem $CombinationItem
 * @property MealMenu $MealMenu
 */
class Recipe extends AppModel {

/**
 * Display field
 *
 * @var string
 */
	public $displayField = 'recipe_name';

/**
 * Validation rules
 *
 * @var array
 */
	public $validate = array(
		'recipe_name' => array(
			'notEmpty' => array(
				'rule' => array('notEmpty'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
		),
		'description' => array(
			'notEmpty' => array(
				'rule' => array('notEmpty'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
		),
		'also_known_as' => array(
			'notEmpty' => array(
				'rule' => array('notEmpty'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
		),
	);

	//The Associations below have been created with all possible keys, those that are not needed can be removed

/**
 * hasMany associations
 *
 * @var array
 */
	public $hasMany = array(
		'CombinationItem' => array(
			'className' => 'CombinationItem',
			'foreignKey' => 'recipe_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		),
		'MealMenu' => array(
			'className' => 'MealMenu',
			'foreignKey' => 'recipe_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		)
	);

        public function delete($id = null, $cascade = true) {
            if ($id == null) {
                $id = $this->id;
            }
            $path = $this->read(array('image'), $id);
            $vale=explode('ofood/',$path['Recipe']['image']);
            unlink($vale[1]);
            parent::delete($id, $cascade);
        }
        public function beforeSave($options = array()) {
            App::uses("HtmlHelper", "View/Helper");
            $html = new HtmlHelper(new View());
            if($this->data[$this->alias]['image']['name']!=""){
                $ext=pathinfo($this->data[$this->alias]['image']['name'], PATHINFO_EXTENSION);
                $image_name = date('YmdHis').rand(1,999) . "." . $ext;
                $destination="files/recipe_images/".$image_name;
                move_uploaded_file($this->data[$this->alias]['image']['tmp_name'],$destination);
                $this->data[$this->alias]['image'] =$html->url("/files/recipe_images/".$image_name,true);
            }
            parent::beforeSave($options);
        }
}
