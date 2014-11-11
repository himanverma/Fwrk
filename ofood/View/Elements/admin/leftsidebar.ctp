<!-- Left side column. contains the logo and sidebar -->
<aside class="left-side sidebar-offcanvas">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <!--<img src="../img/avatar3.png" class="img-circle" alt="User Image" />-->
                <!--<img src="<?php //echo $this->Html->url('img/avatar3.png',array('class'=>"img-circle"));?>" />-->
                <?php echo $this->Html->image('avatar3.png',array('class'=>"img-circle"));?>
            </div>
            <div class="pull-left info">
                <p>Hello, Administrator</p>

                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
<!--        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search..."/>
                <span class="input-group-btn">
                    <button type='submit' name='seach' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
                </span>
            </div>
        </form>-->
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-dashboard"></i>
                    <span>Customers</span>
                    <!--<small class="badge pull-right bg-green">new</small>-->
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/Customers/');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                    <li><a href="<?php echo $this->Html->url('/Customers/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                </ul>
            </li>
            
            
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-circle"></i>
                    <span>Chefs (Food Vendors)</span>
                    <!--<small class="badge pull-right bg-green">new</small>-->
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/vendors/');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                    <li><a href="<?php echo $this->Html->url('/vendors/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                </ul>
            </li>
            
            
            
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-users"></i>
                    <span>Recipes (Food Items)</span>
                    <!--<small class="badge pull-right bg-green">new</small>-->
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/recipes/');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                    <li><a href="<?php echo $this->Html->url('/recipes/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                </ul>
            </li>
            
            
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-folder"></i>
                    <span>Combinations</span>
                    <!--<small class="badge pull-right bg-green">new</small>-->
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/combinations/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                    <li><a href="<?php echo $this->Html->url('/combinations/');?>"><i class="fa fa-angle-double-right"></i> List All(Today)</a></li>
                    <li><a href="<?php echo $this->Html->url('/combinations/');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                </ul>
            </li>
            
            
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-folder"></i>
                    <span>Days</span>
                    <!--<small class="badge pull-right bg-green">new</small>-->
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/vendor_days/');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                    <li><a href="<?php echo $this->Html->url('/vendor_days/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                </ul>
            </li>
            
                        
            <li  class="treeview">
                <a href="">
                    <i class="fa fa-th"></i> <span>Coupons Management</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/admin/coupons');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                    <li><a href="<?php echo $this->Html->url('/admin/coupons/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                </ul>
            </li>
            
            <li  class="treeview">
                <a href="">
                    <i class="fa fa-th"></i> <span>Mail-Templates</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="<?php echo $this->Html->url('/admin/mailtemplates');?>"><i class="fa fa-angle-double-right"></i> List All</a></li>
                    <li><a href="<?php echo $this->Html->url('/admin/mailtemplates/add');?>"><i class="fa fa-angle-double-right"></i> Add New</a></li>
                </ul>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
