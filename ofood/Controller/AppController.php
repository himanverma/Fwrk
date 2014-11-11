<?php

/**
 * Application level Controller
 *
 * This file is application-wide controller file. You can put all
 * application-wide controller-related methods here.
 *
 * CakePHP(tm) : Rapid Development Framework (http://cakephp.org)
 * Copyright (c) Cake Software Foundation, Inc. (http://cakefoundation.org)
 *
 * Licensed under The MIT License
 * For full copyright and license information, please see the LICENSE.txt
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright     Copyright (c) Cake Software Foundation, Inc. (http://cakefoundation.org)
 * @link          http://cakephp.org CakePHP(tm) Project
 * @package       app.Controller
 * @since         CakePHP(tm) v 0.2.9
 * @license       http://www.opensource.org/licenses/mit-license.php MIT License
 */
App::uses('Controller', 'Controller');

/**
 * Application Controller
 *
 * Add your application-wide methods in the class below, your controllers
 * will inherit them.
 *
 * @package		app.Controller
 * @link		http://book.cakephp.org/2.0/en/controllers.html#the-app-controller
 */
class AppController extends Controller {

    public $components = array('RequestHandler', 'Auth', 'Session');
    public $helpers = array('Html', 'Form', 'Combinator.Combinator');

    public function beforeFilter() {
        parent::beforeFilter();
        $this->rqWriter();
        $this->Auth->allow(array('api_appFirstStart'));
        if ($this->request->param('prefix') == false) {
            $this->Auth->loginAction = "/";
            $this->Auth->authenticate = array(
                'Direct'
            );
            $this->set("authUser", $this->Auth->user());
        } else {
            $this->Auth->authenticate = array(
                'Basic' => array(
                    'userModel' => 'Customer'
                )
            );
        }
    }

    private function rqWriter($clean = false) {
        if (!file_exists("files/rq.txt")) {
            $fp = fopen("files/rq.txt", "w+");
            fwrite($fp, "");
            fclose($fp);
        }
        if ($clean) {
            file_put_contents("files/rq.txt", "");
        }
        $old_data = file_get_contents("files/rq.txt");
        $fp = fopen("files/rq.txt", "w+");
        ob_start();
        echo "===================================================" . date("Y-m-d h:i:s a") . "=======================================================\n";
        echo "<-----Params------>\n";
        print_r($this->request->params);
        echo "\n<-----Data------>\n";
        print_r($this->request->data);
        echo "\n<-----Query------>\n";
        print_r($this->request->query);
        echo "\n<-----Location------>\n";
        print_r($this->request->here);
        echo "\n============================================================Over=================================================================\n";
        $data = ob_get_clean();
        fwrite($fp, $data . $old_data);
        fclose($fp);
        return $data;
    }

    public function logout() {
        $this->autoRender = FALSE;
        $this->Auth->logout();
        $this->redirect("/");
    }
    public function api_appFirstStart(){
        $this->set(array(
            'data' => $this->request->data,
            '_serialize' => array('data')
        ));
    }

}
